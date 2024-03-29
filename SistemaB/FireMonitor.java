/******************************************************************************************************************
* File:FireMonitor.java
******************************************************************************************************************/
import InstrumentationPackage.*;
import EventPackage.*;
import java.util.*;
import javax.swing.JOptionPane;

//import FireConsole;

class FireMonitor extends Thread
{
	private EventManagerInterface em = null;// Interface object to the event manager
	private String EvtMgrIP = null;			// Event Manager IP address
	boolean Registered = true;				// Signifies that this class is registered with an event manager.
	boolean isActive = true;				// Signifies if monitoring security is active
	MessageWindow mw = null;				// This is the message window
	Indicator ai;							// Alarm indicator
	Indicator si;							// Sprayer indicator
	boolean confirma = true;				// Indica si es necesario mostrar la confirmacin


	public FireMonitor()
	{
		// event manager is on the local system

		try
		{

			// Here we create an event manager interface object. This assumes
			// that the event manager is on the local machine

			em = new EventManagerInterface();

		}

		catch (Exception e)
		{
			System.out.println("FireMonitor::Error instantiating event manager interface: " + e);
			Registered = false;

		} // catch

	} //Constructor

	public FireMonitor(String EvmIpAddress )
	{
		// event manager is not on the local system

		EvtMgrIP = EvmIpAddress;

		try
		{

			// Here we create an event manager interface object. This assumes
			// that the event manager is NOT on the local machine

			em = new EventManagerInterface( EvtMgrIP );
		}

		catch (Exception e)
		{
			System.out.println("FireMonitor::Error instantiating event manager interface: " + e);
			Registered = false;

		} // catch

	} // Constructor

	public void run()
	{
		Event Evt = null;				// Event object
		EventQueue eq = null;			// Message Queue
		int EvtId = 0;					// User specified event ID

		String CurrentState = "";		// Current fire alarms

		int	Delay = 1000;				// The loop delay (1 second)
		boolean Done = false;			// Loop termination flag
		boolean ON = true;				// Used to turn Security
		boolean OFF = false;			// Used to turn off Security


		if (em != null)
		{
			// Now we create the ECS status and message panel
			// Note that we set up two indicators that are initially yellow. This is
			// because we do not know if the temperature/humidity is high/low.
			// This panel is placed in the upper left hand corner and the status
			// indicators are placed directly to the right, one on top of the other

			mw = new MessageWindow("Fire Monitoring Console", 0, 0);

			ai = new Indicator ("  Fire Detected: ",mw.GetX()+ mw.Width(), 0);
			si = new Indicator ("Sprayer Working: ", mw.GetX()+ mw.Width(), ai.Height());


			mw.WriteMessage( "Registered with the event manager." );

	    	try
	    	{
				mw.WriteMessage("   Participant id: " + em.GetMyId() );
				mw.WriteMessage("   Registration Time: " + em.GetRegistrationTime() );

			} // try

	    	catch (Exception e)
			{
				System.out.println("Error:: " + e);

			} // catch

			/********************************************************************
			** Here we start the main simulation loop
			*********************************************************************/

			while ( !Done )
			{



				// Here we get our event queue from the event manager

				try
				{
					eq = em.GetEventQueue();

				} // try

				catch( Exception e )
				{
					mw.WriteMessage("Error getting event queue::" + e );

				} // catch

				// If there are messages in the queue, we read through them.
				// We are looking for EventIDs = 1 or 2. Event IDs of 1 are temperature
				// readings from the temperature sensor; event IDs of 2 are humidity sensor
				// readings. Note that we get all the messages at once... there is a 1
				// second delay between samples,.. so the assumption is that there should
				// only be a message at most. If there are more, it is the last message
				// that will effect the status of the temperature and humidity controllers
				// as it would in reality.

				int qlen = eq.GetSize();

				for ( int i = 0; i < qlen; i++ )
				{
					Evt = eq.GetEvent();

					if ( Evt.GetEventId() == 7 ) // Fire reading
					{
						try
						{
							CurrentState = Evt.GetMessage();

						} // try

						catch( Exception e )
						{
							mw.WriteMessage("Error reading fire alarm: " + e);

						} // catch

					} // if

					// If the event ID == 99 then this is a signal that the simulation
					// is to end. At this point, the loop termination flag is set to
					// true and this process unregisters from the event manager.

					if ( Evt.GetEventId() == 99 )
					{
						Done = true;

						try
						{
							em.UnRegister();

				    	} // try

				    	catch (Exception e)
				    	{
							mw.WriteMessage("Error unregistering: " + e);

				    	} // catch

				    	mw.WriteMessage( "\n\nSimulation Stopped. \n");

						// Get rid of the indicators. The message panel is left for the
						// user to exit so they can see the last message posted.

						ai.dispose();
						si.dispose();
					} // if

				} // for

				if(isActive){



					if(CurrentState.equalsIgnoreCase("A1")){ //

						mw.WriteMessage("Fire:: ALERT! Fire Detected");
						ai.SetLampColorAndMessage("Fire Detected", 3); //
						try
						{
							if(confirma){
								ConfirmDialog dialog = new ConfirmDialog();
								int op = dialog.showConfirmDialog("Activar roceadores:");
								if(op == 0){ //Yes
									eventFire(9, "S1");
								}
								confirma = false;
							}
						} // try

						catch( Exception e )
						{
							System.out.println( "Sleep error:: " + e );

						} // catch

					}
					if (CurrentState.equalsIgnoreCase("A0")){
						confirma = true;
						mw.WriteMessage("Fire:: Fire not detected");
						ai.SetLampColorAndMessage("Room OK", 1); // Window is ok

					}

					if(CurrentState.equalsIgnoreCase("S1")){

						mw.WriteMessage("Fire:: ALERT! Sprayers Activated");
						si.SetLampColorAndMessage("Sprayer ON", 3); // Door is broken

					}
					if(CurrentState.equalsIgnoreCase("S0")) {

						mw.WriteMessage("Fire:: Sprayer active: False");
						si.SetLampColorAndMessage("Sprayer OFF", 1); // Door is ok

					}




				}


				// This delay slows down the sample rate to Delay milliseconds

				try
				{
					Thread.sleep( Delay );

				} // try

				catch( Exception e )
				{
					System.out.println( "Sleep error:: " + e );

				} // catch

			} // while

		} else {

			System.out.println("Unable to register with the event manager.\n\n" );

		} // if

	} // main

	/***************************************************************************
	* CONCRETE METHOD:: IsRegistered
	* Purpose: This method returns the registered status
	*
	* Arguments: none
	*
	* Returns: boolean true if registered, false if not registered
	*
	* Exceptions: None
	*
	***************************************************************************/

	public boolean IsRegistered()
	{
		return( Registered );

	} // SetTemperatureRange


	/***************************************************************************
	* CONCRETE METHOD:: Halt
	* Purpose: This method posts an event that stops the environmental control
	*		   system.
	*
	* Arguments: none
	*
	* Returns: none
	*
	* Exceptions: Posting to event manager exception
	*
	***************************************************************************/

	public void Halt()
	{
		mw.WriteMessage( "***HALT MESSAGE RECEIVED - SHUTTING DOWN SYSTEM***" );

		// Here we create the stop event.

		Event evt;

		evt = new Event( (int) 99, "XXX" );

		// Here we send the event to the event manager.

		try
		{
			em.SendEvent( evt );

		} // try

		catch (Exception e)
		{
			System.out.println("Error sending halt message:: " + e);

		} // catch

	} // Halt

	public void Activate()
	{
		mw.WriteMessage( "***ACTIVATE MESSAGE RECEIVED - ACTIVATING SYSTEM***" );

		isActive = true;
	} // Activate

	public void Desactivate()
	{
		mw.WriteMessage( "***DESACTIVATE MESSAGE RECEIVED - DESACTIVATING SYSTEM***" );

		isActive = false;
	} // Desactivate

	public void eventFire(int event, String messageTxt)
	{

		// Here we create the event.

		Event evt;

		evt = new Event( event, messageTxt );

		// Here we send the event to the event manager.

		try
		{
			em.SendEvent( evt );

		} // try

		catch (Exception e)
		{
			System.out.println("Error sending event message:: " + e);

		} // catch

	} // Event

} // ECSMonitor
