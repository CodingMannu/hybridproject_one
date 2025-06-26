package utils;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;

public class EmailSender extends TestBase {

	public static String getLatestReportFilePath() {
		String reportsDir = System.getProperty("user.dir") + "/test-output-result/reports";
		File folder = new File(reportsDir);

		if (!folder.exists() || folder.listFiles() == null) {
			logger.error("Reports directory not found: " + reportsDir);
			return null;
		}

		return Arrays.stream(folder.listFiles((dir, name) -> name.endsWith(".html")))
				.max(Comparator.comparingLong(File::lastModified)).map(File::getAbsolutePath).orElse(null);
	}

	public static void sendExtentReportByEmail(String reportPath, String toEmail, String fromEmail,
			String appPassword) {
		try {
			File reportFile = new File(reportPath);
			if (!reportFile.exists()) {
				logger.error("Report file not found: " + reportPath);
				return;
			}

			URL reportUrl = reportFile.toURI().toURL();

			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(fromEmail, appPassword));
			email.setSSLOnConnect(true);
			email.setFrom(fromEmail, "Automation Reports");
			email.setSubject("Automation Test Report");

			email.setMsg("Hi,\n\nPlease find the attached automation test execution report.\n\nRegards,\nQA Team");
			email.addTo(toEmail);
			email.attach(reportUrl, reportFile.getName(), "Extent Report");

			email.send();
			logger.info("📧 Report emailed successfully to: " + toEmail);

		} catch (Exception e) {
			logger.error("❌ Failed to send email.", e);
		}
	}
}
