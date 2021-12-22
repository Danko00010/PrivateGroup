package ru.privatee.bot;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;

import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.model.BillStatus;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.in.Customer;
import com.qiwi.billpayments.sdk.model.in.PaymentInfo;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import com.qiwi.billpayments.sdk.model.out.ResponseStatus;

import ru.privatee.bot.object.BillPay;

public class Qiwi {
private static String secretKey = "eyJ2ZXJzaW9uIjoiUDJQIiwiZGF0YSI6eyJwYXlpbl9tZXJjaGFudF9zaXRlX3VpZCI6InhwcmVvOC0wMCIsInVzZXJfaWQiOiI3OTUxMTI1NjY1NCIsInNlY3JldCI6IjEzYjRmMTIwMWZhMWEyNWRkYTQwNjFhNjM0MWQyZDg1NzkyNjQ1YTk4M2JhOWFlNDExMmM0NmMwYTA1YTJiOWEifX0";
private static String publicKey = "48e7qUxn9T7RyYE1MVZswX1FRSbE6iyCj2gCRwwF3Dnh5XrasNTx3BGPiMsyXQFNKQhvukniQG8RTVhYm3iPyXp7BEQiiJGbyQ3VNyhCWSsDLGZ6qLG5nMZg83a5jfUtCRzD1asHsxDqJMUk5EE2SasB6pzJDKzADB4csGxyJ8VmG6w1rP34rwGhWqCH4";
private static BillPaymentClient client;
private static int i;
private BillPaymentClient fclient;
public Qiwi() {
	i = 0;
	secretKey = Config.getString("QiwiSecretKey");
	publicKey = Config.getString("QiwiPublicKey");
	client = BillPaymentClientFactory.createDefault(secretKey);
	fclient = BillPaymentClientFactory.createDefault("eyJ2ZXJzaW9uIjoiUDJQIiwiZGF0YSI6eyJwYXlpbl9tZXJjaGFudF9zaXRlX3VpZCI6InhwcmVvOC0wMCIsInVzZXJfaWQiOiI3OTUxMTI1NjY1NCIsInNlY3JldCI6IjU2ZTdiMDY1NjljYTE0MDA5M2RhMWFlNjk3ZGNiMjE1ZDYzYmUyZmMwMzFhZDNkNDZlODUxYjUzZDU3NDI4NWMifX0");
}
public static BillResponse checkBill(BillPay bill) {
	 System.out.println(bill.getBill());
	BillResponse res = client.getBillInfo(bill.getBill());
	return res;
}
public static BillPay getPaymentUrl(double amou, long chatId) {

	 CreateBillInfo billInfo = new CreateBillInfo(
             UUID.randomUUID().toString(),
             new MoneyAmount(
                     BigDecimal.valueOf(amou),
                     Currency.getInstance("RUB")
             ),
             "comment",
             ZonedDateTime.now().plusDays(3),
            new Customer("siple@gmail.com", UUID.randomUUID().toString(), "79512267644"),
             "http://merchant.ru/success"
     );

	
BillResponse response = null;
try {
	response = client.createBill(billInfo);
} catch (URISyntaxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
/*	 client = BillPaymentClientFactory.createDefault(secretKey);
	 MoneyAmount amount = new MoneyAmount(
		        BigDecimal.valueOf(amou),
		        Currency.getInstance("RUB")
		);
		String billId = UUID.randomUUID().toString();
		String successUrl = "https://merchant.com/payment/success?billId=cc961e8d-d4d6-4f02-b737-2297e51fb48e";
	s
	String paymentUrl = client.createPaymentForm(new PaymentInfo(publicKey, amount, billId, successUrl));
	
	*/
if(i<=5) {
	i=0;
	try {
		response = client.createBill(billInfo);
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
i++;
return new BillPay(response.getBillId(), response.getPayUrl(), chatId);
}

	

}
