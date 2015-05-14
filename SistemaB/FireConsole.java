/******************************************************************************************************************
* File:FireConsole.java
*
******************************************************************************************************************/

import TermioPackage.*;
import EventPackage.*;
import java.util.*;

public class FireConsole {

	String optionS = "";

	public static void main(String args[])
	{
    	Termio UserInput = new Termio();	// Termio IO Object, keyboard
		boolean Done = false;				// Main loop flag
		String Option = null;				// Menu choice from user
		boolean Error = false;				// Error flag
		FireMonitor Monitor = null;	// The environmental control system monitor
		String CurrentState;
		String AlarmState = "A0";
		String SprayerState = "S0";
		int Act_Dea = 0;
		

		/////////////////////////////////////////////////////////////////////////////////
		// Get the IP address of the event manager
		/////////////////////////////////////////////////////////////////////////////////

 		if ( args.length != 0 )
 		{
			// event manager is not on the local system

			Monitor = new FireMonitor( args[0] );

		} else {

			Monitor = new FireMonitor();

		} // if

		Monitor.setFireConsole(FireConsole);

		// Here we check to see if registration worked. If ef is null then the
		// event manager interface was not properly created.

		if (Monitor.IsRegistered() )
		{
			Monitor.start(); // Here we start the monitoring and control thread

			while (!Done)
			{
				// Here, the main thread continues and provides the main menu

				System.out.println( "\n\n\n\n" );
				System.out.println( "Environmental Control Fire System Command Console: \n" );

				if (args.length != 0)
					System.out.println( "Using event manger at: " + args[0] + "\n" );
				else
					System.out.println( "Using local event manger \n" );

				System.out.println( "Manipulate Fire System "  );
				System.out.println( "Select an Option: \n" );
				System.out.println( "1: Activate" );
				System.out.println( "2: Deactivate" );
				System.out.println( "3: Set Fire" );
				System.out.println( "4: Turn off Sprayers" );
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
					Monitor.Activate();

				} // if

				//////////// option 2 ////////////

				if ( Option.equals( "2" ) )
				{
					// Here we get the humidity ranges
					// Cuando esta desactivado, los eventos de seguridad no se reportan
					// Simular eventos de seguridad					
					Monitor.Desactivate();


				} // if

				if ( Option.equals( "3" ) )
				{
					Error = true;

					while (Error)
					{
						System.out.print( "\nEnter 1/0 to activate or deactivate fire simulation >>> " );
						Option = UserInput.KeyboardReadString();

						if (UserInput.IsNumber(Option))
						{
							int opt = Integer.parseInt(Option);
							if(opt == 0 || opt == 1){
								Error = false;
								Act_Dea = Integer.parseInt(Option);								
								AlarmState = "A"+Act_Dea;
								Monitor.eventFire(-8,AlarmState);							


							}
							

						} else {

							System.out.println( "Not a valid option, please try again..." );

						} // if

					} // while


				} // if

				if ( Option.equals( "4" ) )
				{
					
					SprayerState = "S0";
					Monitor.eventFire(9,SprayerState);


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

  	public String confirmSprayer(){

  		Termio userInputS = new Termio();
  		
		boolean errorS = true;

		TimerTask task = new TimerTask(){
			public void run(){
				if( optionS.equals("") ){
					System.out.println( "you input nothing. sprayers will activate" );
					optionS = "S1";
				}
			}
		};

		Timer timer = new Timer();
		timer.schedule( task, 15*1000 );

		while (errorS){

			System.out.print( "\nEnter 1/0 to activate or deactivate sprayers, it will activate in 15 sec. if theres no input >>> " );
			optionS = userInputS.KeyboardReadString();

			if (userInputS.IsNumber(optionS)){
				int opt = Integer.parseInt(optionS);
				if(opt == 0 || opt == 1){
				
					return optionS;
				}
				

			} else {

				System.out.println( "Not a valid option, please try again..." );

			} // if

		}
		timer.cancel();
		return optionS;

  	}

}
