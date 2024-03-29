/******************************************************************************************************************
* File:TemperatureController.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*	1.0 March 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description:
*
* This class simulates a device that controls a heater and chiller. It polls the event manager for event ids = 5
* and reacts to them by turning on or off the heater or chiller. The following command are valid strings for con
* trolling the heater and chiller:
*
*	H1 = heater on
*	H0 = heater off
*	C1 = chillerer on
*	C0 = chiller off
*
* The state (on/off) is graphically displayed on the terminal in the indicator. Command messages are displayed in
* the message window. Once a valid command is recieved a confirmation event is sent with the id of -5 and the command in
* the command string.
*
* Parameters: IP address of the event manager (on command line). If blank, it is assumed that the event manager is
* on the local machine.
*
* Internal Methods:
*	static private void ConfirmMessage(EventManagerInterface ei, String m )
*
******************************************************************************************************************/
import InstrumentationPackage.*;
import EventPackage.*;
import java.util.*;

class TemperatureController
{
	public static void main(String args[])
	{
		String EvtMgrIP;				// Event Manager IP address
		Event Evt = null;				// Event object
		EventQueue eq = null;			// Message Queue
		int EvtId = 0;					// User specified event ID
		EventManagerInterface em = null;// Interface object to the event manager
		boolean HeaterState = false;	// Heater state: false == off, true == on
		boolean ChillerState = false;	// Chiller state: false == off, true == on
		int	Delay = 2500;				// The loop delay (2.5 seconds)
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
			
			MessageWindow mw = new MessageWindow("Temperature Controller Status Console", WinPosX, WinPosY);
			
			// Put the status indicators under the panel...
			
			Indicator ci = new Indicator ("Chiller OFF", mw.GetX(), mw.GetY()+mw.Height());
			Indicator hi = new Indicator ("Heater OFF", mw.GetX()+(ci.Width()*2), mw.GetY()+mw.Height());

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

				for ( int i = 0; i < qlen; i++ )
				{
					Evt = eq.GetEvent();

					if ( Evt.GetEventId() == 5 )
					{
						if (Evt.GetMessage().equalsIgnoreCase("H1")) // heater on
						{
							HeaterState = true;
							mw.WriteMessage("Received heater on event" );

							// Confirm that the message was recieved and acted on

							ConfirmMessage( em, "H1" );

						} // if

						if (Evt.GetMessage().equalsIgnoreCase("H0")) // heater off
						{
							HeaterState = false;
							mw.WriteMessage("Received heater off event" );

							// Confirm that the message was recieved and acted on

							ConfirmMessage( em, "H0" );

						} // if

						if (Evt.GetMessage().equalsIgnoreCase("C1")) // chiller on
						{
							ChillerState = true;
							mw.WriteMessage("Received chiller on event" );

							// Confirm that the message was recieved and acted on

							ConfirmMessage( em, "C1" );

						} // if

						if (Evt.GetMessage().equalsIgnoreCase("C0")) // chiller off
						{
							ChillerState = false;
							mw.WriteMessage("Received chiller off event" );

							// Confirm that the message was recieved and acted on

							ConfirmMessage( em, "C0" );

						} // if

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

						hi.dispose();
						ci.dispose();

					} // if

				} // for

				// Update the lamp status

				if (HeaterState)
				{
					// Set to green, heater is on

					hi.SetLampColorAndMessage("HEATER ON", 1);

				} else {

					// Set to black, heater is off
					hi.SetLampColorAndMessage("HEATER OFF", 0);

				} // if

				if (ChillerState)
				{
					// Set to green, chiller is on

					ci.SetLampColorAndMessage("CHILLER ON", 1);

				} else {

					// Set to black, chiller is off

					ci.SetLampColorAndMessage("CHILLER OFF", 0);

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

		Event evt = new Event( (int) -5, m );

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

		Event evt = new Event( (int) 11, "09-TemperatureController-Controller that shows temperature levels" );

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