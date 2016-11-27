package profiles;

import java.util.Calendar;

public class DifferedPaymentProfile {

	private Calendar endDifferedDate;
	private char differedType;
    private boolean differFlag=false;
	
	public DifferedPaymentProfile(char type, Calendar startDate, int duration, Calendar refDate){
		if(type!='N'){//N = no differed payment
			Calendar endDifferedDate=(Calendar)startDate.clone();
			endDifferedDate.add(Calendar.MONTH, duration);
			if(endDifferedDate.after(refDate)){//if it's still in differed payment
				this.differFlag=true;
				this.differedType=type;
				this.endDifferedDate=endDifferedDate;
		    }

		}
	}

	//getters, setters
	public Calendar getEndDifferedDate() {
		return endDifferedDate;
	}

	public void setEndDifferedDate(Calendar endDifferedDate) {
		this.endDifferedDate = endDifferedDate;
	}

	public char getDifferedType() {
		return differedType;
	}

	public void setDifferedType(char type) {
		this.differedType = type;
	}
	
	public boolean isDiffered() {
		return differFlag;
	}

	public void setDifferFlag(boolean flag) {
		this.differFlag = flag;
	}
	
}
