/******************************************************************************************************************
* File:ECSConsole.java
* Course: 17655
* Project: Assignment 3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*	1.0 February 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description: This class is the console for the museum environmental control system. This process consists of two
* threads. The ECSMonitor object is a thread that is started that is responsible for the monitoring and control of
* the museum environmental systems. The main thread provides a text interface for the user to change the temperature
* and humidity ranges, as well as shut down the system.
*
* Parameters: None
*
* Internal Methods: None
*
******************************************************************************************************************/

import TermioPackage.*;
import EventPackage.*;

public class SecurityConsole {


	public static void main(String args[])
	{
    	Termio UserInput = new Termio();	// Termio IO Object, keyboard
		boolean Done = false;				// Main loop flag
		String Option = null;				// Menu choice from user
		//Event Evt = null;					// Event object
		//boolean Error = false;				// Error flag
		
		SecurityMonitor Monitor = null;			// The environmental control system monitor
		boolean manageSystem = false;        // Activate and deactivate system, on = true, off = false
		

		/////////////////////////////////////////////////////////////////////////////////
		// Get the IP address of the event manager
		/////////////////////////////////////////////////////////////////////////////////

 		if ( args.length != 0 )
 		{
			// event manager is not on the local system

			Monitor = new SecurityMonitor( args[0] );

		} else {

			Monitor = new SecurityMonitor();

		} // if


		// Here we check to see if registration worked. If ef is null then the
		// event manager interface was not properly created.

		if (Monitor.IsRegistered() )
		{
			Monitor.start(); // Here we start the monitoring and control thread

			while (!Done)
			{
				// Here, the main thread continues and provides the main menu

				System.out.println( "\n\n\n\n" );
				System.out.println( "Environmental Control Security System (ECSSecurity) Command Console: \n" );

				if (args.length != 0)
					System.out.println( "Using event manger at: " + args[0] + "\n" );
				else
					System.out.println( "Using local event manger \n" );

				System.out.println( "Manipulate Security System "  );
				System.out.println( "Select an Option: \n" );
				System.out.println( "1: Activate" );
				System.out.println( "2: Deactivate" );
				System.out.println( "X: Stop System\n" );
				System.out.print( "\n>>>> " );
				Option = UserInput.KeyboardReadString();

				//////////// option 1 ////////////

				if ( Option.equals( "1" ) )
				{
						/* Here we activate security system
						 * Cuando el sistema esta activado, intrusiones de seguridad
						   * deben ser reportadas a los operadores a traves  del monitor de seguridad.
						   */
							
					System.out.print( "\n Reportando instrucciones de seguridad>>> " );
					manageSystem = true;
					//monitor

				} // if

				//////////// option 2 ////////////

				if ( Option.equals( "2" ) )
				{
					// Here we get the humidity ranges
					// Cuando esta desactivado, los eventos de seguridad no se reportan
					// Simular eventos de seguridad
					manageSystem = false;
					//monitor


				} // if

				//////////// option X ////////////

				if ( Option.equalsIgnoreCase( "X" ) )
				{
					// Here the user is done, so we set the Done flag and halt
					// the environmental control system. The monitor provides a method
					// to do this. Its important to have processes release their queues
					// with the event manager. If these queues are not released these
					// become dead queues and they collect events and will eventually
					// cause problems for the event manager.

					Monitor.Halt();
					Done = true;
					System.out.println( "\nConsole Stopped... Exit monitor mindow to return to command prompt." );
					Monitor.Halt();

				} // if

			} // while

		} else {

			System.out.println("\n\nUnable start the monitor.\n\n" );

		} // if

  	} // main

}
