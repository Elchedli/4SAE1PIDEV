package com.Utils;



public class GeneratePdfReport {

	
	
/*	private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class);
    private List<Voyage> listVoyage;
    
    public GeneratePdfReport(List<Voyage> listVoyage) {
        this.listVoyage = listVoyage;
    }

    public static ByteArrayInputStream VoyageReport(List<Voyage> voyages) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

       /* try {

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{1, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
           // hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Name", headFont));
          //  hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Population", headFont));
           // hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            for (Voyage v : voyages) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(v.getDestination()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

              /*  cell = new PdfPCell(new Phrase(city.getName()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(city.getPopulation())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        }

        PdfPTable table = new PdfPTable(3);
        document.open();
        try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
    
  /*  private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
       // cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
       // font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("User ID", font));
         
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Full Name", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Roles", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Enabled", font));
        table.addCell(cell);       
    }
     
    private void writeTableData(PdfPTable table) {
        for (Voyage v : listVoyage) {
         //   table.addCell(String.valueOf(user.getId()));
            table.addCell(v.getDestination());
           // table.addCell(user.getFullName());
        //    table.addCell(user.getRoles().toString());
        //    table.addCell(String.valueOf(user.isEnabled()));
        }
    }
    
    
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
     //   font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("List of Users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }*/
}
