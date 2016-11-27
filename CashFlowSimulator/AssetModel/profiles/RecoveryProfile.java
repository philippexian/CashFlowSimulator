package profiles;

public class RecoveryProfile {
	
	private char recoveryType;
	private int startRecovery;
	private int endRecovery;
	
	//only 3 attributes: recovery type, the start and the end of the recovery
	
	public RecoveryProfile(char type, int startPeriod, int endPeriod){
		this.recoveryType=type;
		this.endRecovery=endPeriod;
		//2 types: I: infine, L: linear
		if(type=='I'){
			this.startRecovery=endPeriod;//recover all in once
		}
		else{
			this.startRecovery=startPeriod;
		}
	}

	//getters and setters
	public char getRecoveryType() {
		return recoveryType;
	}

	public void setRecoveryType(char recoveryType) {
		this.recoveryType = recoveryType;
	}

	public int getStartRecovery() {
		return startRecovery;
	}

	public void setStartRecovery(int startRecovery) {
		this.startRecovery = startRecovery;
	}

	public int getEndRecovery() {
		return endRecovery;
	}

	public void setEndRecovery(int endRecovery) {
		this.endRecovery = endRecovery;
	}
	
	

}
