package bk.robotmotionaction.entity;

public class SendMoney {
	String personSend;
	String address;
	int accNumber;
	String accName;
	String bankName;
	String quantityMoney;

	public String getPersonSend() {
		return personSend;
	}

	public void setPersonSend(String personSend) {
		this.personSend = personSend;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(int accNumber) {
		this.accNumber = accNumber;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getQuantityMoney() {
		return quantityMoney;
	}

	public void setQuantityMoney(String quantityMoney) {
		this.quantityMoney = quantityMoney;
	}

}
