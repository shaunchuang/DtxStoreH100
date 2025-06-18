package itri.sstc.framework.core.httpd;

import java.util.concurrent.Executor;
import java.util.logging.Logger;

/**
 *
 * @author schung
 */
public class HttpExecutor implements Executor {

    public final static Logger logger = Logger.getLogger(HttpExecutor.class.getName());

    public HttpExecutor() {
        logger.fine("Create HttpExecutor instance");
    }

    @Override
    public void execute(Runnable command) {
        logger.fine(String.format("Create new Thread to run command %s", command.toString()));
        new Thread(command).start();
    }
    
}
