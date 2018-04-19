package proxiupload;

import java.io.IOException;

import org.junit.Test;

public class DaemonRunTest {

	
	@Test
	public void testProximaxDaemon() {
		try {
			String cmd = "ipfs daemon";
			Process p = Runtime.getRuntime().exec(cmd);
			
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
