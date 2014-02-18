/**
 * This file is part of the I3A project.
 *
 *
 * @copyright  	(c) the I3A GBS Muenchen
 * @author     	i3a <it@lala-rob.de>
 * @license    	GPLv3 http://www.gnu.org/licenses/gpl.html
 * @version 	0.0.1
 *
 */
package i3a.car;

import javax.swing.SwingUtilities;

public class I3aCar {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new GuiStart();
            }
        });
        
    }
}
