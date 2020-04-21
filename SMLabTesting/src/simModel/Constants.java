package simModel;

class Constants 
{
	/* Constants */
	// Define constants as static
	// Example: protected final static double realConstant = 8.0;
	public static final int C1 = 0;
	public static final int C2 = 1;
	public static final int C3 = 2;
	public static final int C4 = 3;
	public static final int C5 = 4;
	public static final int LA = 5;
	public static final int[] DEFAULT_CID_ARRAY = new int[] {C1, C2, C3, C4, C5};
	
	/* Buffer lengths */
	public static final int INPUT_BUFFER_SIZE = 3;  //  Define in the Initialise class
	public static final int LA_INPUT_BUFFER_LENGTH = 5;
	public static final int LA_INPUT_BUFFER_MODIFIED_LENGTH = 2;
	
	public static final int LOOP_SIZE = 48; 

}
