
public class Interrupter
{
    public static void main(String[] args) {
        Thread worker = new Thread (new InterruptibleThread());
        worker.start();
        
        // now wait 3 seconds before interrupting it
        try { Thread.sleep(3000); } catch (InterruptedException ie) { }
        
        worker.interrupt();
    }
}
