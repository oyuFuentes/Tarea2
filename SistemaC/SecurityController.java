/******************************************************************************************************************
* File:SecurityController.java
*
******************************************************************************************************************/
import InstrumentationPackage.*;
import EventPackage.*;
import java.util.*;

class SecurityController
{
	public static void main(String args[])
	{
		String EvtMgrIP;				// Event Manager IP address
		Event Evt = null;				// Event object
		EventQueue eq = null;			// Message Queue
		int EvtId = 0;					// User specified event ID
		EventManagerInterface em = null;// Interface object to the event manager
		boolean WindowState = false;	// Heater state: false == off, true == on
		boolean DoorState = false;		// Chiller tate: false == off, true == on
		boolean	MovementState = false;
		int	Delay = 1000;				// The loop delay (1 second)
		boolean Done = false;			// Loop termination flag

		/////////////////////////////////////////////////////////////////////////////////
		// Get the IP address of the event manager
		/////////////////////////////////////////////////////////////////////////////////

 		if ( args.length == 0 )
 		{
			// event manager is on the local system

			System.out.println("\n\nAttempting to register on the local machine..." );

			try
			{
				// Here we create an event manager interface object. This assumes
				// that the event manager is on the local machine

				em = new EventManagerInterface();
			}

			catch (Exception e)
			{
				System.out.println("Error instantiating event manager interface: " + e);

			} // catch

		} else {

			// event manager is not on the local system

			EvtMgrIP = args[0];

			System.out.println("\n\nAttempting to register on the machine:: " + EvtMgrIP );

			try
			{
				// Here we create an event manager interface object. This assumes
				// that the event manager is NOT on the local machine

				em = new EventManagerInterface( EvtMgrIP );
			}

			catch (Exception e)
			{
				System.out.println("Error instantiating event manager interface: " + e);

			} // catch

		} // if

		// Here we check to see if registration worked. If ef is null then the
		// event manager interface was not properly created.

		if (em != null)
		{
			System.out.println("Registered with the event manager." );

			/* Now we create the temperature control status and message panel
			** We put this panel about 1/3 the way down the terminal, aligned to the left
			** of the terminal. The status indicators are placed directly under this panel
			*/

			float WinPosX = 0.0f; 	//This is the X position of the message window in terms 
								 	//of a percentage of the screen height
			float WinPosY = 0.3f; 	//This is the Y position of the message window in terms 
								 	//of a percentage of the screen height 
			
			MessageWindow mw = new MessageWindow("Security Controller Status Console", WinPosX, WinPosY);
			
			// Put the status indicators under the panel...
			
			Indicator wi = new Indicator ("WindowState OFF", mw.GetX(), mw.GetY()+mw.Height());
			Indicator di = new Indicator ("DoorState OFF", mw.GetX()+(wi.Width()*3), mw.GetY()+mw.Height());
			Indicator mi = new Indicator ("MovementState OFF", mw.GetX()+(di.Width()*6), mw.GetY()+mw.Height());

			mw.WriteMessage("Registered with the event manager." );

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

				try
				{
					eq = em.GetEventQueue();

				} // try

				catch( Exception e )
				{
					mw.WriteMessage("Error getting event queue::" + e );

				} // catch

				// If there are messages in the queue, we read through them.
				// We are looking for EventIDs = 5, this is a request to turn the
				// heater or chiller on. Note that we get all the messages
				// at once... there is a 2.5 second delay between samples,.. so
				// the assumption is that there should only be a message at most.
				// If there are more, it is the last message that will effect the
				// output of the temperature as it would in reality.

				int qlen = eq.GetSize();
				
				System.out.println("qlen = "+qlen);

				for ( int i = 0; i < qlen; i++ )
				{
					Evt = eq.GetEvent();

					if ( Evt.GetEventId() == 6 )
					{
						
						for (String choice: Evt.GetMessage().split("-")){

							if (choice.equalsIgnoreCase("W1")) // heater on
							{
								WindowState = true;
								

							} // if

							if (choice.equalsIgnoreCase("W0")) // heater off
							{
								WindowState = false;
								

							} // if

							if (choice.equalsIgnoreCase("D1")) // chiller on
							{
								DoorState = true;
								

							} // if

							if (choice.equalsIgnoreCase("D0")) // chiller off
							{
								DoorState = false;
								

							} // if

							if (choice.equalsIgnoreCase("M1")) // chiller on
							{
								MovementState = true;
								

							} // if

							if (choice.equalsIgnoreCase("M0")) // chiller off
							{
								MovementState = false;

							} // if

						}

						mw.WriteMessage("Received security event" );

						// Confirm that the message was recieved and acted on

						ConfirmMessage( em, Evt.GetMessage());

					} // if

					if ( Evt.GetEventId() == 10 )
					{
						PostEcho(em);

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

				// Update the lamp status

				if (WindowState)
				{
					// Set to green, heater is on

					wi.SetLampColorAndMessage("WINDOW BROKEN", 3);

				} else {

					// Set to black, heater is off
					wi.SetLampColorAndMessage("WINDOW OK", 1);

				} // if

				if (DoorState)
				{
					// Set to green, chiller is on

					di.SetLampColorAndMessage("DOOR BROKEN", 3);

				} else {

					// Set to black, chiller is off

					di.SetLampColorAndMessage("DOOR OK", 1);

				} // if

				if (MovementState)
				{
					// Set to green, chiller is on

					mi.SetLampColorAndMessage("MOVEMENT OK", 3);

				} else {

					// Set to black, chiller is off

					mi.SetLampColorAndMessage("MOVEMENT DETECTION", 1);

				} // if

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
	* CONCRETE METHOD:: ConfirmMessage
	* Purpose: This method posts the specified message to the specified event
	* manager. This method assumes an event ID of -5 which indicates a confirma-
	* tion of a command.
	*
	* Arguments: EventManagerInterface ei - this is the eventmanger interface
	*			 where the event will be posted.
	*
	*			 string m - this is the received command.
	*
	* Returns: none
	*
	* Exceptions: None
	*
	***************************************************************************/

	static private void ConfirmMessage(EventManagerInterface ei, String m )
	{
		// Here we create the event.

		Event evt = new Event( (int) -6, m );

		// Here we send the event to the event manager.

		try
		{
			ei.SendEvent( evt );

		} // try

		catch (Exception e)
		{
			System.out.println("Error Confirming Message:: " + e);

		} // catch

	} // PostMessage

	static private void PostEcho(EventManagerInterface ei)
	{
		// Here we create the event.

		Event evt = new Event( (int) 11, "07-SecurityController-Controller that manages security sensors" );

		// Here we send the event to the event manager.

		try
		{
			ei.SendEvent( evt );
			//System.out.println( "Sent Temp Event" );			

		} // try

		catch (Exception e)
		{
			System.out.println( "Error Posting Echo:: " + e );

		} // catch

	} // PostTemperature

} // TemperatureController