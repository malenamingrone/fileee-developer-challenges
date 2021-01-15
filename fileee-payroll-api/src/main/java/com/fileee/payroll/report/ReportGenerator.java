package com.fileee.payroll.report;

import com.fileee.payroll.entity.WageSettlement;
import com.fileee.payroll.error.ReportException;
import com.lowagie.text.DocumentException;
import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class ReportGenerator {

    private static final Logger log = Logger.getLogger(ReportGenerator.class.getName());

    public static ByteArrayInputStream wageSettlementReport(WageSettlement wageSettlement) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Configuration templateConfig = templateConfiguration();
            StringWriter writer = new StringWriter();

            Template template = templateConfig.getTemplate("wage-settlement-template.ftl");
            template.process(createInput(wageSettlement), writer);

            writer.flush();

            String html = writer.toString();

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(out, false);
            renderer.finishPDF();
            out.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException | DocumentException | TemplateException e) {
            log.error("An error occurred generating pdf report.");
            throw new ReportException(e);
        }
    }

    private static Map<String, Object> createInput(WageSettlement wageSettlement) {
        Map<String, Object> input = new HashMap<>();
        input.put("employeeName", wageSettlement.getEmployee().getName());
        input.put("salaryType", wageSettlement.getEmployee().getSalaryType());
        input.put("period", parsePeriod(wageSettlement.getStartDate(), wageSettlement.getEndDate()));
        input.put("totalAmount", parseAmount(wageSettlement.getTotalAmount()));
        return input;
    }

    private static Configuration templateConfiguration() {
        Configuration templateConfig = new Configuration();
        templateConfig.setClassForTemplateLoading(ReportGenerator.class, "/templates/");
        templateConfig.setIncompatibleImprovements(new Version(2, 3, 20));
        templateConfig.setDefaultEncoding("UTF-8");
        return templateConfig;
    }

    private static String parsePeriod(LocalDate since, LocalDate until) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return since.format(formatter).concat(" - ").concat(until.format(formatter));
    }

    private static String parseAmount(BigDecimal amount) {
        return "$" + amount.toPlainString();
    }

}
