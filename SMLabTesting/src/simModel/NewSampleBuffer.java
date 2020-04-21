package simModel;

import java.util.ArrayList;

class NewSampleBuffer {
	int n;
	protected ArrayList<Sample> list = new ArrayList<>();
	
	//UDPs
	protected void addTail(Sample icSample) {
		list.add(icSample);
		n += 1;
	}
	
	protected void addHead(Sample icSample) {
		list.add(0, icSample);
		n += 1;
	}
	
	protected Sample removeHead() {
		Sample icSample = list.remove(0);
		n -= 1;
		return icSample;
	}
}
