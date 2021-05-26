package obps.models;

public class PaymentModes {
	
		private Integer paymentmodecode;
		private String paymentmodedescription;
		private String mode;
		public PaymentModes() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		public PaymentModes(Integer paymentmodecode, String paymentmodedescription, String mode) {
			super();
			this.paymentmodecode = paymentmodecode;
			this.paymentmodedescription = paymentmodedescription;
			this.mode = mode;
		}

		public Integer getPaymentmodecode() {
			return paymentmodecode;
		}
		public void setPaymentmodecode(Integer paymentmodecode) {
			this.paymentmodecode = paymentmodecode;
		}
		public String getPaymentmodedescription() {
			return paymentmodedescription;
		}
		public void setPaymentmodedescription(String paymentmodedescription) {
			this.paymentmodedescription = paymentmodedescription;
		}
		
		/**
		 * @return the mode
		 */
		public String getMode() {
			return mode;
		}

		/**
		 * @param mode the mode to set
		 */
		public void setMode(String mode) {
			this.mode = mode;
		}

		@Override
		public String toString() {
			return "PaymentModes [paymentmodecode=" + paymentmodecode + ", paymentmodedescription="
					+ paymentmodedescription + "]";
		}
		
	}
