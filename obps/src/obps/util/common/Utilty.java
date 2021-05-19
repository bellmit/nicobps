package obps.util.common;

public class Utilty 
{
	public static int getRandomNumber() {
	    //Random r = new Random( System.currentTimeMillis() );
	    //return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
        String num =Math.random()+"";
        return Integer.valueOf(num.substring(2,8));		
	}
}
