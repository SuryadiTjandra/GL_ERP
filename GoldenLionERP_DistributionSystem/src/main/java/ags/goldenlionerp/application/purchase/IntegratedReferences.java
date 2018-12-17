package ags.goldenlionerp.application.purchase;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@Embeddable
@JsonDeserialize(builder=IntegratedReferences.Builder.class)
public class IntegratedReferences {
	
	private String feederVessel;
	
	private String connectingVessel;
	
	private String shippedBy;
	
	//private String integratedReference4;
	
	//private String integratedReference5;

	private IntegratedReferences() {}
	
	private IntegratedReferences(Builder builder) {
		this.feederVessel = builder.feederVessel;
		this.connectingVessel = builder.connectingVessel;
		this.shippedBy = builder.shippedBy;
	}

	public String getFeederVessel() {
		return feederVessel;
	}

	public String getConnectingVessel() {
		return connectingVessel;
	}

	public String getShippedBy() {
		return shippedBy;
	}

	/**
	 * Creates builder to build {@link IntegratedReferences}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link IntegratedReferences}.
	 */
	@JsonPOJOBuilder(withPrefix="")
	public static final class Builder {
		private String feederVessel;
		private String connectingVessel;
		private String shippedBy;

		private Builder() {
		}

		public Builder feederVessel(String feederVessel) {
			this.feederVessel = feederVessel;
			return this;
		}

		public Builder connectingVessel(String connectingVessel) {
			this.connectingVessel = connectingVessel;
			return this;
		}

		public Builder shippedBy(String shippedBy) {
			this.shippedBy = shippedBy;
			return this;
		}

		public IntegratedReferences build() {
			return new IntegratedReferences(this);
		}
	}
	
}
