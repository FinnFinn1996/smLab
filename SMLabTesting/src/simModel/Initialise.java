package simModel;

import java.util.ArrayList;
import java.util.Collections;

import simModel.Tester.Status;
import simulationModelling.ScheduledAction;

class Initialise extends ScheduledAction {
	static SMLabTesting model;
	private static final int NONE = -1;
	private static final Sample NO_SAMPLE = null;

	protected Initialise(SMLabTesting model) {
		this.model = model;
	}

	protected void actionEvent() {
		udpInitInputBuffer();
		udpInitOutputBuffer();
		udpInitSampleHolders();
		udpInitNewSampleBuffer();
		udpPlaceSampleHolder();
		udpInitLoadUnloadDevice();
		udpInitTester();
		udpInitTransportationLoop();
	}

	private void udpInitSampleHolders() {
		model.rSampleHolder = new SampleHolder[model.numSampleHolders];
		for (int sid = 0; sid < model.numSampleHolders; sid++) {
			SampleHolder sampleHolders = new SampleHolder();
			sampleHolders.sample = NO_SAMPLE;
			model.rSampleHolder[sid] = sampleHolders;
		}
	}

	private void udpInitLoadUnloadDevice() {
		LoadUnloadDevice ld = new LoadUnloadDevice();
		ld.busy = false;
		model.rLoadUnloadDevice = ld;
	}

	private void udpInitTester() {
		model.rcTester = new Tester[5][];
		for (int cid : Constants.DEFAULT_CID_ARRAY) {

			model.rcTester[cid] = new Tester[model.numTesters[cid]];
			for (int tid = 0; tid < model.numTesters[cid]; tid++) {
				Tester t = new Tester();
				t.status = Status.IDLE;
				t.timeToFail = model.rvp.uTimeToFailure(cid);
				t.numOps = 0;
				model.rcTester[cid][tid] = t;
			}
		}
	}

	double[] ts = { 0.0, -1.0 };
	int tsix = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationModelling.ScheduledAction#timeSequence()
	 */
	@Override
	protected double timeSequence() {
		return ts[tsix++];
	}

	private void udpInitTransportationLoop() {
		for (int pos = 0; pos < Constants.LOOP_SIZE; pos++) {
			model.rqTransportationLoop.positon[pos] = NONE;
		}
		model.rqTransportationLoop.loadAreaPosition = 0;
	}

	private void udpInitNewSampleBuffer() {
		model.qNewSampleBuffer.n = 0;
	}

	private void udpPlaceSampleHolder() {
		int num = model.numSampleHolders - 1;
		if (model.logicConfiguration == false) {
			while (model.qInputBuffer[Constants.LA].isAvailable() && num >= 0) {
				model.qInputBuffer[Constants.LA].insert(num);
				num -= 1;
			}
		} else {
			while (model.qInputBuffer[Constants.LA].list.size() < Constants.LA_INPUT_BUFFER_MODIFIED_LENGTH
					&& num >= 0) {
				model.qInputBuffer[Constants.LA].insert(num);
				num -= 1;
			}
		}
		ArrayList<Integer> positions = new ArrayList<>();
		for (int i = 0; i < Constants.LOOP_SIZE; i++) {
			positions.add(i);
		}
		Collections.shuffle(positions);
		for (int pos : positions) {
			model.rqTransportationLoop.positon[pos] = num;
			num -= 1;
			if (num < 0)
				break;
		}
	}

	private void udpInitInputBuffer() {
		for (int cid : Constants.DEFAULT_CID_ARRAY) {
			InputBuffer inBuf = new InputBuffer();
			inBuf.capacity = Constants.INPUT_BUFFER_SIZE;
			inBuf.list = new ArrayList<>();
			model.qInputBuffer[cid] = inBuf;
		}
		InputBuffer inBuf = new InputBuffer();
		inBuf.capacity = Constants.LA_INPUT_BUFFER_LENGTH;
		inBuf.list = new ArrayList<>();
		model.qInputBuffer[Constants.LA] = inBuf;
	}

	private void udpInitOutputBuffer() {
		for (int cid : Constants.DEFAULT_CID_ARRAY) {
			OutputBuffer ob = new OutputBuffer();
			ob.list = new ArrayList<>();
			model.qOutputBuffer[cid] = ob;
		}
		OutputBuffer ob = new OutputBuffer();
		ob.list = new ArrayList<>();
		model.qOutputBuffer[Constants.LA] = ob;
	}

}
