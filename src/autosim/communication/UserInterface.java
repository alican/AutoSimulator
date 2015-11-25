package autosim.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Peter Altenberd
 *         (Translated into English by Ronald Moore)
 *         Computer Science Dept.                   Fachbereich Informatik
 *         Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

public class UserInterface {

    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    public String input() throws IOException {
        return stdIn.readLine();
    }

    public void output(String out) {
        System.out.print(out);
    }

    @Override
    public void finalize() throws IOException {
        stdIn.close();
    }
}
