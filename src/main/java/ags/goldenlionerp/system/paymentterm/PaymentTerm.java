package ags.goldenlionerp.system.paymentterm;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0014")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="PYUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="PYDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="PYTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="PYUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="PYDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="PYTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="PYCID"))
})
public class PaymentTerm extends DatabaseEntity {

	@Id
	@Column(name="PYPTC")
	private String paymentTermCode;
	
	@Column(name="PYDESA1")
	private String description = "";
	
	@Column(name="PYDCRT", precision=19, scale=15)
	private BigDecimal discountPercentage = new BigDecimal(0);
	
	@Column(name="PYDCDY")
	private int discountDays;
	
	@Column(name="PYNDTP")
	private int netDaysToPay;
	
	@Column(name="PYNOSP")
	private int numberOfSplitPayment;
	
	@Column(name="PYDTPA")
	private int daysToPayAging;
	
	@Column(name="PYEIR", precision=9, scale=5)
	private BigDecimal effectiveInterestRate = new BigDecimal(0);
	
	@Column(name="PYDUMO")
	private int dueMonth;
	
	@Column(name="PYDUDY")
	private int dueDay;
	
	@Column(name="PYITAR")
	private Boolean interfaceToAccountReceivable = false;
	
	@Column(name="PYITAP")
	private Boolean interfaceToAccountPayable = false;
	
	@Column(name="PYOBID")
	private String objectId = "";

	public String getPaymentTermCode() {
		return paymentTermCode;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}

	public int getDiscountDays() {
		return discountDays;
	}

	public int getNetDaysToPay() {
		return netDaysToPay;
	}

	public int getNumberOfSplitPayment() {
		return numberOfSplitPayment;
	}

	public int getDaysToPayAging() {
		return daysToPayAging;
	}

	public BigDecimal getEffectiveInterestRate() {
		return effectiveInterestRate;
	}

	public int getDueMonth() {
		return dueMonth;
	}

	public int getDueDay() {
		return dueDay;
	}

	public boolean getInterfaceToAccountReceivable() {
		return interfaceToAccountReceivable;
	}

	public boolean getInterfaceToAccountPayable() {
		return interfaceToAccountPayable;
	}

	void setPaymentTermCode(String paymentTermCode) {
		this.paymentTermCode = paymentTermCode;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	void setDiscountDays(int discountDays) {
		this.discountDays = discountDays;
	}

	void setNetDaysToPay(int netDaysToPay) {
		this.netDaysToPay = netDaysToPay;
	}

	void setNumberOfSplitPayment(int numberOfSplitPayment) {
		this.numberOfSplitPayment = numberOfSplitPayment;
	}

	void setDaysToPayAging(int daysToPayAging) {
		this.daysToPayAging = daysToPayAging;
	}

	void setEffectiveInterestRate(BigDecimal effectiveInterestRate) {
		this.effectiveInterestRate = effectiveInterestRate;
	}

	void setDueMonth(int dueMonth) {
		this.dueMonth = dueMonth;
	}

	void setDueDay(int dueDay) {
		this.dueDay = dueDay;
	}

	void setInterfaceToAccountReceivable(boolean interfaceToAccountReceivable) {
		this.interfaceToAccountReceivable = interfaceToAccountReceivable;
	}

	void setInterfaceToAccountPayable(boolean interfaceToAccountPayable) {
		this.interfaceToAccountPayable = interfaceToAccountPayable;
	}

	public String getObjectId() {
		return objectId;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	
}
