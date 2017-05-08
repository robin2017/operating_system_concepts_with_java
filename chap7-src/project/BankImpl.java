/**
 * Created by robin on 2017/5/4.
 */
public class BankImpl implements Bank {
    int[] resource;
    public BankImpl(int[] resource){
        this.resource=resource;
    }
    @Override
    public void addCustomer(int threadNum, int[] maxDemand) {

    }

    @Override
    public void getState() {

    }

    @Override
    public boolean requestResources(int threadNum, int[] request) {
        return false;
    }

    @Override
    public void releaseResources(int threadNum, int[] release) {

    }
}
