package ags.goldenlionerp.application.purchase;

import javax.persistence.Embeddable;

@Embeddable
public class PurchaseOptions {

	private String containerSize;
	
	private String stuffingType;
	
	private String containerLoadType;

	//private String purchaseOption4;
	
	//private String purchaseOption5;
	
	//private String purchaseOption6;
	
	//private String purchaseOption7;
	
	//private String purchaseOption8;
	
	//private String purchaseOption9;
	
	//private String purchaseOption10;
	
	private PurchaseOptions() {}
	
	private PurchaseOptions(Builder builder) {
		this.containerSize = builder.containerSize;
		this.stuffingType = builder.stuffingType;
		this.containerLoadType = builder.containerLoadType;
	}

	
	public String getContainerSize() {
		return containerSize;
	}

	public String getStuffingType() {
		return stuffingType;
	}

	public String getContainerLoadType() {
		return containerLoadType;
	}

	/**
	 * Creates builder to build {@link PurchaseOptions}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link PurchaseOptions}.
	 */
	public static final class Builder {
		private String containerSize;
		private String stuffingType;
		private String containerLoadType;

		private Builder() {
		}

		public Builder containerSize(String containerSize) {
			this.containerSize = containerSize;
			return this;
		}

		public Builder stuffingType(String stuffingType) {
			this.stuffingType = stuffingType;
			return this;
		}

		public Builder containerLoadType(String containerLoadType) {
			this.containerLoadType = containerLoadType;
			return this;
		}

		public PurchaseOptions build() {
			return new PurchaseOptions(this);
		}
	}
	
	
	
}
