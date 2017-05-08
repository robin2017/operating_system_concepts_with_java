package deadlock.deadlock_2avoid;

/**
 * Created by robin on 2017/5/4.
 */
public class BankImpl implements Bank {
    int[][] tmp_maxinum={{7,5,3},
            {3,2,2},
            {9,0,2},
            {2,2,2},
            {4,3,3}};
    int[][] tmp_allocation={{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}};
    //银行通过如下的六个数据跟踪资源
    int numOfCustomers;
    int numOfResources;
    int[] available;
    int[][] maxinum;
    int[][] allocation;
    int[][] need;

    public BankImpl(int[] resource){
        //初始的可用值就是资源数量
        this.available=resource;
        this.numOfResources=this.available.length;
        numOfCustomers=5;
        this.maxinum=tmp_maxinum;
        this.allocation=tmp_allocation;
    }
    @Override
    public void addCustomer(int threadNum, int[] maxDemand) {
            //这个在初始化里做了
    }

    @Override
    public void getState() {

    }

    @Override
    public boolean requestResources(int threadNum, int[] request) {
        //检测是否超出自己所需
        getNeed();

        if(!isSmallArr(request,need[threadNum],numOfResources))
            System.out.println("超出自己所需");

        if(!isSmallArr(request,available,numOfResources))
            System.out.println("超出系统资源");

        available=reduceMatrix(available,request,numOfResources);

        addMatrix(allocation[threadNum],request,numOfResources);
        reduceMatrix(need[threadNum],request,numOfResources);
        if(isSafe()) {
            print_allocation_available();
            return true;
        }
        else{//恢复
            addMatrix(available, request, numOfResources);
            reduceMatrix(allocation[threadNum], request, numOfResources);
            addMatrix(need[threadNum], request, numOfResources);
            System.out.println("申请失败，恢复原来");
            print_allocation_available();
            return false;
        }
    }

    private void getNeed() {
        need=new int[5][3];
        for(int i=0;i<numOfCustomers;i++)
            for(int j=0;j<numOfResources;j++)
                need[i][j]=maxinum[i][j]-allocation[i][j];
    }

    private void print_allocation_available(){
        System.out.println("allocation");
        for(int i=0;i<numOfCustomers;i++) {
            for (int j = 0; j < numOfResources; j++)
                System.out.print(" " + allocation[i][j]);
            System.out.println();
        }
        System.out.println("avaiable");
        for(int i=0;i<numOfResources;i++)
            System.out.print(" "+available[i]);
    }


    @Override
    public void releaseResources(int threadNum, int[] release) {

    }

    private boolean isSmallArr(int[] request, int[] ints, int numOfResources) {
        for(int i=0;i<numOfResources;i++)
            if(request[i]>ints[i])
                return false;
        return true;
    }

    //调用need,allocation,available
    private boolean isSafe(){
        //两个中间值
        boolean[] finish=new boolean[numOfCustomers];
        //绝对不能这样写啊，浪费时间
      //  int[] work=available;

        int[] work=new int[numOfResources];
        for(int i=0;i<numOfResources;i++)
            work[i]=available[i];
        //两层循环：满足need<available条件，则赋值true，且跳出第一个循环；若cnt大于num，则跳出第二个循环

        boolean isFinish=false;
        while(true){
            int cnt=0;
            while(true){
                if(finish[cnt]==false&&conform(need[cnt],available,numOfResources)){
                    finish[cnt]=true;
                    System.out.print("-->"+cnt+"  ");
                    addMatrix(work,allocation[cnt],numOfResources);
                    break;
                }
                if(++cnt==numOfCustomers){
                    isFinish=true;
                    break;
                }
            }
            if(isFinish)
                break;
        }
        boolean isSuccess=true;
        for(int i=0;i<numOfCustomers;i++)
            if(!finish[i])
                isSuccess=false;
        if(isSuccess)
            System.out.println("success");
        else
            System.out.println("not success");
        return isSuccess;
    }


    private int[] addMatrix(int[] work, int[] ints,int num) {
        for(int i=0;i<num;i++)
            work[i]+=ints[i];
        return work;
    }
    private int[] reduceMatrix(int[] work, int[] ints,int num) {
        for(int i=0;i<num;i++)
            work[i]-=ints[i];
        return work;
    }

    private boolean conform(int[] a,int[] b,int num) {
        for(int i=0;i<num;i++){
            if(a[i]>b[i])
                    return false;
        }
        return true;
    }

//
//    public static void main(String[] args){
//        int[] ttt={1,2,3};
//        new BankImpl(ttt).test1();
//
//    }
//    public void test1(){
//        int[] a={1,2,3};
//        int[] b={4,5,6};
//        addMatrix(a,b,3);
//    }
//
//    public void test() {
//        int[][] test_allocation={{0,1,0},{2,0,0},{3,0,2},{2,1,1},{0,0,2}};
//        int[] test_available={3,3,2};
//        int[][] test_need={{7,4,3},{1,2,2},{6,0,0},{0,1,1},{4,3,1}};
//        allocation=test_allocation;
//        need=test_need;
//        available=test_available;
//        isSafe();
//    }
}
