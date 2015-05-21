/******************************************************************************************************************
* File:SecurityMonitor.java
******************************************************************************************************************/
import InstrumentationPackage.*;
import EventPackage.*;
import java.util.*;

class SecurityMonitor extends Thread
{
	private EventManagerInterface em = null;// Interface object to the event manager
	private String EvtMgrIP = null;			// Event Manager IP address
	boolean Registered = true;				// Signifies that this class is registered with an event manager.
	boolean isActive = true;				// Signifies if monitoring security is active
	MessageWindow mw = null;				// This is the message window
	Indicator wi;							// Window broken indicator
	Indicator di;							// Door broken indicator
	Indicator mi;							// Movement detection indicator

	public SecurityMonitor()
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
			System.out.println("ECSSecurityMonitor::Error instantiating event manager interface: " + e);
			Registered = false;

		} // catch

	} //Constructor

	public SecurityMonitor( String EvmIpAddress )
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
			System.out.println("ECSSecurityMonitor::Error instantiating event manager interface: " + e);
			Registered = false;

		} // catch

	} // Constructor

	public void run()
	{
		Event Evt = null;				// Event object
		EventQueue eq = null;			// Message Queue
		int EvtId = 0;					// User specified event ID

		String CurrentState = "";		// Current security alarms

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

			mw = new MessageWindow("ECS Security Monitoring Console", 0, 0);

			wi = new Indicator ("Window Broken",mw.GetX()+ mw.Width(), 0);
			di = new Indicator ("Door Broken", mw.GetX()+ mw.Width(), wi.Height());
			mi = new Indicator ("Movement Detection", mw.GetX()+ mw.Width(), di.Height()*2, 2 );

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

					if ( Evt.GetEventId() == 3 ) // Security reading
					{
						try
						{
							CurrentState = Evt.GetMessage();

						} // try

						catch( Exception e )
						{
							mw.WriteMessage("Error reading security alarm: " + e);

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

						wi.dispose();
						di.dispose();
						mi.dispose();
					} // if

				} // for

				if(isActive && Evt.GetMessage() != null){

					if(Evt.GetMessage().equalsIgnoreCase("W1")){ //Window

						mw.WriteMessage("Security:: �ALERT! Window broken");
						wi.SetLampColorAndMessage("Window broken", 3); // Window is broken


					}
					if (Evt.GetMessage().equalsIgnoreCase("W0")){



						mw.WriteMessage("Security:: Window broken: False");
						wi.SetLampColorAndMessage("Window OK", 1); // Window is ok

					}


					if(Evt.GetMessage().equalsIgnoreCase("D1")){

						mw.WriteMessage("Security:: ALERT! Door broken");
						di.SetLampColorAndMessage("Door Broken", 3); // Door is broken


					}
					if(Evt.GetMessage().equalsIgnoreCase("D0")) {

						mw.WriteMessage("Security:: Door broken: False");
						di.SetLampColorAndMessage("Door OK", 1); // Door is ok


					}

					if(Evt.GetMessage().equalsIgnoreCase("M1")){


						mw.WriteMessage("Security:: ALERT! Movement detection");
						mi.SetLampColorAndMessage("Movement Broken", 3); // Movement detection


					}
					if(Evt.GetMessage().equalsIgnoreCase("M0")) {


						mw.WriteMessage("Security:: Movement detection: False");
						mi.SetLampColorAndMessage("Movement OK", 1); // Movement is ok

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

	public void eventSensor(String messageTxt)
	{
		System.out.println("Enters here "+messageTxt);
		// Here we create the event.

		Event evt;

		evt = new Event( (int) 6, messageTxt );

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

} // ECSMonitor
