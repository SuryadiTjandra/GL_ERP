package ags.goldenlionerp.application.setups.fiscalyear;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0018")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="FPUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="FPDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="FPTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="FPUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="FPDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="FPTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="FPCID"))
})
public class FiscalYear extends DatabaseEntity<FiscalYearPK> {

	public final static int NUMBER_OF_PERIOD = 12;
	
	@EmbeddedId
	@JsonUnwrapped
	private FiscalYearPK pk;
	
	@Column(name="FPDATE00")
	private Timestamp startDate;
	
	@Column(name="FPDATE01")
	private Timestamp endDateOfPeriod1;
	
	@Column(name="FPDATE02")
	private Timestamp endDateOfPeriod2;
	
	@Column(name="FPDATE03")
	private Timestamp endDateOfPeriod3;
	
	@Column(name="FPDATE04")
	private Timestamp endDateOfPeriod4;
	
	@Column(name="FPDATE05")
	private Timestamp endDateOfPeriod5;
	
	@Column(name="FPDATE06")
	private Timestamp endDateOfPeriod6;
	
	@Column(name="FPDATE07")
	private Timestamp endDateOfPeriod7;
	
	@Column(name="FPDATE08")
	private Timestamp endDateOfPeriod8;
	
	@Column(name="FPDATE09")
	private Timestamp endDateOfPeriod9;
	
	@Column(name="FPDATE10")
	private Timestamp endDateOfPeriod10;
	
	@Column(name="FPDATE11")
	private Timestamp endDateOfPeriod11;
	
	@Column(name="FPDATE12")
	private Timestamp endDateOfPeriod12;
	
	//@Column(name="FPDATE13")
	//private Timestamp endDateOfPeriod13;
	
	//@Column(name="FPDATE14")
	//private Timestamp endDateOfPeriod14;

	public FiscalYearPK getPk() {
		return pk;
	}

	public LocalDate getStartDate() {
		return startDate.toLocalDateTime().toLocalDate();
	}

	public LocalDate getEndDateOfPeriod1() {
		return getEndDateOfPeriod(1);
	}

	public LocalDate getEndDateOfPeriod2() {
		return getEndDateOfPeriod(2);
	}

	public LocalDate getEndDateOfPeriod3() {
		return getEndDateOfPeriod(3);
	}

	public LocalDate getEndDateOfPeriod4() {
		return getEndDateOfPeriod(4);
	}

	public LocalDate getEndDateOfPeriod5() {
		return getEndDateOfPeriod(5);
	}

	public LocalDate getEndDateOfPeriod6() {
		return getEndDateOfPeriod(6);
	}

	public LocalDate getEndDateOfPeriod7() {
		return getEndDateOfPeriod(7);
	}

	public LocalDate getEndDateOfPeriod8() {
		return getEndDateOfPeriod(8);
	}

	public LocalDate getEndDateOfPeriod9() {
		return getEndDateOfPeriod(9);
	}

	public LocalDate getEndDateOfPeriod10() {
		return getEndDateOfPeriod(10);
	}

	public LocalDate getEndDateOfPeriod11() {
		return getEndDateOfPeriod(11);
	}

	public LocalDate getEndDateOfPeriod12() {
		return getEndDateOfPeriod(12);
	}
	
	public LocalDate getEndDateOfPeriod(int n) {
		Timestamp endDate = null;
		switch(n) {
			case 1: endDate = endDateOfPeriod1; break;
			case 2: endDate = endDateOfPeriod2; break;
			case 3: endDate = endDateOfPeriod3; break;
			case 4: endDate = endDateOfPeriod4; break;
			case 5: endDate = endDateOfPeriod5; break;
			case 6: endDate = endDateOfPeriod6; break;
			case 7: endDate = endDateOfPeriod7; break;
			case 8: endDate = endDateOfPeriod8; break;
			case 9: endDate = endDateOfPeriod9; break;
			case 10: endDate = endDateOfPeriod10; break;
			case 11: endDate = endDateOfPeriod11; break;
			case 12: endDate = endDateOfPeriod12; break;
			default: throw new IllegalArgumentException("Illegal period number: " + n);
		}
		if (endDate != null) {
			return endDate.toLocalDateTime().toLocalDate();
		} else if (n == 1) {
			return getStartDate().withDayOfMonth(getStartDate().getMonth().length(getStartDate().isLeapYear()));
		} else if (n == NUMBER_OF_PERIOD){
			return getStartDate().plusYears(1).minusDays(1);
		} else {
			LocalDate lastEndDate = getEndDateOfPeriod(n - 1);
			
			LocalDate startPlusMonths = getStartDate().plusMonths(n - 1);
			LocalDate supposedEndDate = startPlusMonths.withDayOfMonth(startPlusMonths.getMonth().length(startPlusMonths.isLeapYear()));
			
			return lastEndDate.compareTo(supposedEndDate) > 0 ? lastEndDate: supposedEndDate;
		}
	}

	//public LocalDate getEndDateOfPeriod13() {
	//	return endDateOfPeriod13.toLocalDateTime().toLocalDate();
	//}

	//public LocalDate getEndDateOfPeriod14() {
	//	return endDateOfPeriod14.toLocalDateTime().toLocalDate();
	//}

	void setPk(FiscalYearPK pk) {
		this.pk = pk;
	}

	void setStartDate(LocalDate startDate) {
		this.startDate = Timestamp.valueOf(startDate.atStartOfDay());
	}

	void setEndDateOfPeriod1(LocalDate endDateOfPeriod1) {
		this.endDateOfPeriod1 = Timestamp.valueOf(endDateOfPeriod1.atStartOfDay());
	}

	void setEndDateOfPeriod2(LocalDate endDateOfPeriod2) {
		this.endDateOfPeriod2 = Timestamp.valueOf(endDateOfPeriod2.atStartOfDay());
	}

	void setEndDateOfPeriod3(LocalDate endDateOfPeriod3) {
		this.endDateOfPeriod3 = Timestamp.valueOf(endDateOfPeriod3.atStartOfDay());
	}

	void setEndDateOfPeriod4(LocalDate endDateOfPeriod4){
		this.endDateOfPeriod4 = Timestamp.valueOf(endDateOfPeriod4.atStartOfDay());
	}

	void setEndDateOfPeriod5(LocalDate endDateOfPeriod5) {
		this.endDateOfPeriod5 = Timestamp.valueOf(endDateOfPeriod5.atStartOfDay());
	}

	void setEndDateOfPeriod6(LocalDate endDateOfPeriod6) {
		this.endDateOfPeriod6 = Timestamp.valueOf(endDateOfPeriod6.atStartOfDay());
	}

	void setEndDateOfPeriod7(LocalDate endDateOfPeriod7) {
		this.endDateOfPeriod7 = Timestamp.valueOf(endDateOfPeriod7.atStartOfDay());
	}

	void setEndDateOfPeriod8(LocalDate endDateOfPeriod8) {
		this.endDateOfPeriod8 = Timestamp.valueOf(endDateOfPeriod8.atStartOfDay());
	}

	void setEndDateOfPeriod9(LocalDate endDateOfPeriod9) {
		this.endDateOfPeriod9 = Timestamp.valueOf(endDateOfPeriod9.atStartOfDay());
	}

	void setEndDateOfPeriod10(LocalDate endDateOfPeriod10) {
		this.endDateOfPeriod10 = Timestamp.valueOf(endDateOfPeriod10.atStartOfDay());
	}

	void setEndDateOfPeriod11(LocalDate endDateOfPeriod11) {
		this.endDateOfPeriod11 = Timestamp.valueOf(endDateOfPeriod11.atStartOfDay());
	}

	void setEndDateOfPeriod12(LocalDate endDateOfPeriod12) {
		this.endDateOfPeriod12 = Timestamp.valueOf(endDateOfPeriod12.atStartOfDay());
	}
	
	void setEndDateOfPeriod(int n, LocalDate endDate) {
		switch(n) {
			case 1: setEndDateOfPeriod1(endDate); break;
			case 2: setEndDateOfPeriod2(endDate); break;
			case 3: setEndDateOfPeriod3(endDate); break;
			case 4: setEndDateOfPeriod4(endDate); break;
			case 5: setEndDateOfPeriod5(endDate); break;
			case 6: setEndDateOfPeriod6(endDate); break;
			case 7: setEndDateOfPeriod7(endDate); break;
			case 8: setEndDateOfPeriod8(endDate); break;
			case 9: setEndDateOfPeriod9(endDate); break;
			case 10: setEndDateOfPeriod10(endDate); break;
			case 11: setEndDateOfPeriod11(endDate); break;
			case 12: setEndDateOfPeriod12(endDate); break;
			default: throw new IllegalArgumentException("Illegal period number: " + n);
		}
	}

	@Override
	public FiscalYearPK getId() {
		return getPk();
	}

	//void setEndDateOfPeriod13(LocalDate endDateOfPeriod13) {
	//	this.endDateOfPeriod13 = Timestamp.valueOf(endDateOfPeriod13.atStartOfDay());
	//}

	//void setEndDateOfPeriod14(LocalDate endDateOfPeriod14) {
	//	this.endDateOfPeriod14 = Timestamp.valueOf(endDateOfPeriod14.atStartOfDay());
	//}
	
	
}
