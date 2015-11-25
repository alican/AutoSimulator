package models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Bench {

    static String addr;
    static int port;

    private static Bench instance;
    static Long beginTest;
    static int failed;
    static List<BenchTest> benchTests;

    private Bench(){

        beginTest =  System.currentTimeMillis();
        benchTests = new ArrayList<>();
    }

    public synchronized void isFailed(){
        failed++;
    }

    static public void setInfo(String _addr, int _port){
        addr = _addr;
        port = _port;
    }

    public static synchronized Bench getInstance () {
        if (Bench.instance == null) {
            Bench.instance = new Bench();
        }
        return Bench.instance;
    }

    public synchronized BenchTest start(){
        BenchTest test = new BenchTest();
        benchTests.add(test);
        return test;
    }


    public long timeEnd() {
        long ends = benchTests.get(benchTests.size() - 1).ends;
        return ends - beginTest;
    }

    public double timeEnd(TimeUnit unit) {
        return unit.convert(timeEnd(), TimeUnit.MILLISECONDS);
    }


    public String results(){

        if (benchTests.isEmpty()){
            return "Keine Testdaten vorhanden.";
        }

        return String.format(
                "Server address: \t\t %s \n" +
                "Server port: \t\t\t %d \n\n" +
                "Complete requests: \t\t %d\n" +
                "Failed requests: \t\t %d \n" +
                "Time taken for test: \t %d ms \n" +
                "Requests per second: \t %f \n" +
                 "Time per request: \t\t %f ms\n"

                ,
                addr,
                port,
                benchTests.size(),
                failed,
                timeEnd(),
                benchTests.size()/(timeEnd()*1.0/1000),
                timeEnd()*1.0/benchTests.size()
        );
    }



    public class BenchTest{

        long starts;
        long ends;

        public BenchTest(){

            starts = System.currentTimeMillis();
        }

        public long stop(){
            ends = System.currentTimeMillis();
            return ends - starts;
        }

        public long time() {
            long ends = System.currentTimeMillis();
            return ends - starts;
        }

        public long time(TimeUnit unit) {
            return unit.convert(time(), TimeUnit.MILLISECONDS);
        }

    }
}
