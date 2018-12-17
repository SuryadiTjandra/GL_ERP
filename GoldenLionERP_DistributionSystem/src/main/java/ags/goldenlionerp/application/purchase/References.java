package ags.goldenlionerp.application.purchase;

import javax.persistence.Embeddable;

@Embeddable
public class References {
	private String jobNo;
	
	private String shipmentId;
	
	private String packinglistId;
	
	private String deliveryOrderNumber;
	
	private String sealNumber;

	private References() {}
	
	private References(Builder builder) {
		this.jobNo = builder.jobNo;
		this.shipmentId = builder.shipmentId;
		this.packinglistId = builder.packinglistId;
		this.deliveryOrderNumber = builder.deliveryOrderNumber;
		this.sealNumber = builder.sealNumber;
	}

	public String getJobNo() {
		return jobNo;
	}

	public String getShipmentId() {
		return shipmentId;
	}

	public String getPackinglistId() {
		return packinglistId;
	}

	public String getDeliveryOrderNumber() {
		return deliveryOrderNumber;
	}

	public String getSealNumber() {
		return sealNumber;
	}

	/**
	 * Creates builder to build {@link References}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link References}.
	 */
	public static final class Builder {
		private String jobNo;
		private String shipmentId;
		private String packinglistId;
		private String deliveryOrderNumber;
		private String sealNumber;

		private Builder() {
		}

		public Builder jobNo(String jobNo) {
			this.jobNo = jobNo;
			return this;
		}

		public Builder shipmentId(String shipmentId) {
			this.shipmentId = shipmentId;
			return this;
		}

		public Builder packinglistId(String packinglistId) {
			this.packinglistId = packinglistId;
			return this;
		}

		public Builder deliveryOrderNumber(String deliveryOrderNumber) {
			this.deliveryOrderNumber = deliveryOrderNumber;
			return this;
		}

		public Builder sealNumber(String sealNumber) {
			this.sealNumber = sealNumber;
			return this;
		}

		public References build() {
			return new References(this);
		}
	}
	
	
}
