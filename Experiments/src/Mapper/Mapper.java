package Mapper;

public class Mapper {
	public static float map(float n,float start1,float stop1,float start2,float stop2) {
		float newval = (n - start1) / (stop1 - start1) * (stop2 - start2) + start2;
		return newval;
	}
}
