/**
 * @author jcrandall
 * @file Pellet.java
 * @date 2/27/2017
 * @version OCC CS272 Spring 2017 Tuesday evening
 */

package edu.orangecoastcollege.cs272.jcrandall.p02;
/**
 * Represents a Pellet Character, child of <code>Character</code> has its own movement, to string, and constructor methods.
 *
 */
public class Pellet extends Character 
{
	/**
	 * Has no effect for pellets have no soul... I mean can't move.
	 * @param board which will be moved on
	 */
	@Override public void move(Board board)
	{
		// Pellets don't move. They wish they could. But they can't. Harsh deal really.
		// Blessed are we who are not Pellets.
		// But by the grace of god go I.
		// A pellet.
/*		
		
	

quu..__
 $$$b  `---.__
  "$$b        `--.                          ___.---uuudP
   `$$b           `.__.------.__     __.---'      $$$$"              .
     "$b          -'            `-.-'            $$$"              .'|
       ".                                       d$"             _.'  |
         `.   /                              ..."             .'     |
           `./                           ..::-'            _.'       |
            /                         .:::-'            .-'         .'
           :                          ::''\          _.'            |
          .' .-.             .-.           `.      .'               |
          : /'$$|           .@"$\           `.   .'              _.-'
         .'|$u$$|          |$$,$$|           |  <            _.-'
         | `:$$:'          :$$$$$:           `.  `.       .-'
         :                  `"--'             |    `-.     \
        :##.       ==             .###.       `.      `.    `\
        |##:                      :###:        |        >     >
        |#'     `..'`..'          `###'        x:      /     /
         \                                   xXX|     /    ./
          \                                xXXX'|    /   ./
          /`-.                                  `.  /   /
         :    `-  ...........,                   | /  .'
         |         ``:::::::'       .            |<    `.
         |             ```          |           x| \ `.:``.
         |                         .'    /'   xXX|  `:`M`M':.
         |    |                    ;    /:' xXXX'|  -'MMMMM:'
         `.  .'                   :    /:'       |-'MMMM.-'
          |  |                   .'   /'        .'MMM.-'
          `'`'                   :  ,'          |MMM<
            |                     `'            |tbap\
             \                                  :MM.-'
              \                 |              .''
               \.               `.            /
                /     .:::::::.. :           /
               |     .:::::::::::`.         /
               |   .:::------------\       /
              /   .''               >::'  /
              `',:                 :    .'	

		source-http://www.chris.com/ascii/index.php?art=video%20games/pokemon
		
*/	}
	/**
	 * returns a string representation of a pellet
	 * @return string representation of a pellet
	 */
	@Override public String toString()
	{
		return "*";
	}
	/**
	 * instantiates a new pellet character with a position
	 * @param pos of the new pellet
	 */
	public Pellet(int[] pos)
	{
		super(pos);
	}
	
	
}
