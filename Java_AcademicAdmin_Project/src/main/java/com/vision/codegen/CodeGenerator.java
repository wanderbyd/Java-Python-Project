package com.vision.codegen;
import java.io.FileWriter;
import java.io.IOException;
// 연구중인 소스 --------------   여기서는 아직 사용하지 않음 
public class CodeGenerator {
	 public static void main(String[] args) {
	        generateEntityClass("Student");
	        generateControllerClass("Student");
	        generateListTemplate("Student");
	        generateDetailTemplate("Student");
	        generateFormTemplate("Student");
	        generateEditTemplate("Student");
	    }
	// Generate JPA Entity Class
	    public static void generateEntityClass(String className) {
	        String entityClass = String.format("import jakarta.persistence.Column;\n" +
	                "import jakarta.persistence.Entity;\n" +
	                "import jakarta.persistence.Id;\n" +
	                "import jakarta.persistence.Table;\n\n" +
	                "@Entity\n" +
	                "@Table(name = \"%ss\")\n" +
	                "public class %s {\n\n" +
	                "    @Id\n" +
	                "    @Column(name = \"ssn\")\n" +
	                "    private String ssn;\n\n" +
	                "    @Column(name = \"fullname\")\n" +
	                "    private String fullname;\n\n" +
	                "    @Column(name = \"phonenumber\")\n" +
	                "    private String phonenumber;\n\n" +
	                "    @Column(name = \"businessregistrationnumber\")\n" +
	                "    private String businessregistrationnumber;\n\n" +
	                "    // Getters and setters\n" +
	                "}\n", className.toLowerCase(), className);

	        saveToFile(className + ".java", entityClass);
	    }

	    // Generate Controller Class
	    public static void generateControllerClass(String className) {
	        String controllerClass = String.format("import org.springframework.beans.factory.annotation.Autowired;\n" +
	                "import org.springframework.stereotype.Controller;\n" +
	                "import org.springframework.ui.Model;\n" +
	                "import org.springframework.web.bind.annotation.*;\n\n" +
	                "@Controller\n" +
	                "@RequestMapping(\"/%ss\")\n" +
	                "public class %sController {\n\n" +
	                "    private final %sService %sService;\n\n" +
	                "    @Autowired\n" +
	                "    public %sController(%sService %sService) {\n" +
	                "        this.%sService = %sService;\n" +
	                "    }\n\n" +
	                "    @GetMapping(\"\")\n" +
	                "    public String list%s(Model model) {\n" +
	                "        model.addAttribute(\"%ss\", %sService.getAll%s());\n" +
	                "        return \"/%ss/list\";\n" +
	                "    }\n\n" +
	                "    @GetMapping(\"/{%s}\")\n" +
	                "    public String view%s(@PathVariable String %s, Model model) {\n" +
	                "        %s %s = %sService.get%sById(%s);\n" +
	                "        if (%s == null) {\n" +
	                "            // Handle case where %s is not found\n" +
	                "            return \"error\"; // You can create an error page\n" +
	                "        }\n" +
	                "        model.addAttribute(\"%s\", %s);\n" +
	                "        return \"/%ss/detail\";\n" +
	                "    }\n\n" +
	                "    @GetMapping(\"/add\")\n" +
	                "    public String create%sForm(Model model) {\n" +
	                "        return \"/%ss/form\";\n" +
	                "    }\n\n" +
	                "    @PostMapping(\"/create\")\n" +
	                "    public String create%s(@ModelAttribute %s %s) {\n" +
	                "        %sService.save%s(%s);\n" +
	                "        return \"redirect:/%ss\";\n" +
	                "    }\n\n" +
	                "    @GetMapping(\"/edit/{%s}\")\n" +
	                "    public String edit%sForm(@PathVariable String %s, Model model) {\n" +
	                "        %s %s = %sService.get%sById(%s);\n" +
	                "        if (%s == null) {\n" +
	                "            // Handle case where %s is not found\n" +
	                "            return \"error\"; // You can create an error page\n" +
	                "        }\n" +
	                "        model.addAttribute(\"%s\", %s);\n" +
	                "        return \"/%ss/edit\";\n" +
	                "    }\n\n" +
	                "    @PostMapping(\"/update/{%s}\")\n" +
	                "    public String update%s(@PathVariable String %s, @ModelAttribute %s %s) {\n" +
	                "        %s.set%s(%s); // Update existing %s\n" +
	                "        %sService.save%s(%s);\n" +
	                "        return \"redirect:/%ss\";\n" +
	                "    }\n\n" +
	                "    @PostMapping(\"/delete/{%s}\")\n" +
	                "    public String delete%s(@PathVariable String %s) {\n" +
	                "        %sService.delete%s(%s);\n" +
	                "        return \"redirect:/%ss\";\n" +
	                "    }\n" +
	                "}\n", className.toLowerCase(), className, className, className.toLowerCase(),
	                className, className, className.toLowerCase(), className, className,
	                className, className, className, className.toLowerCase(),
	                className, className, className, className.toLowerCase(), className,
	                className, className, className, className.toLowerCase(), className, className,
	                className.toLowerCase(), className.toLowerCase(), className, className, className,
	                className.toLowerCase(), className, className, className.toLowerCase(), className,
	                className, className, className.toLowerCase(), className, className, className.toLowerCase(),
	                className.toLowerCase(), className, className, className, className, className, className,
	                className, className.toLowerCase(), className, className.toLowerCase(), className, className);

	        saveToFile(className + "Controller.java", controllerClass);
	    }
	    // Generate List Template
	    public static void generateListTemplate(String className) {
	        String listTemplate = String.format("<!DOCTYPE html>\n" +
	                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
	                "<head>\n" +
	                "    <title>%s List</title>\n" +
	                "</head>\n" +
	                "<body>\n" +
	                "    <h1>%s List</h1>\n" +
	                "    <table>\n" +
	                "        <thead>\n" +
	                "            <tr>\n" +
	                "                <th>학번</th>\n" +
	                "                <th>성명</th>\n" +
	                "                <th>전화번호</th>\n" +
	                "                <th>근무처사업자등록번호</th>\n" +
	                "                <th>Action</th>\n" +
	                "            </tr>\n" +
	                "        </thead>\n"+
	                "        <tbody>\n" +
	                "            <tr th:each=\"%s : ${%ss}\">\n" +
	                "                <td th:text=\"${%s.ssn}\"></td>\n" +
	                "                <td th:text=\"${%s.fullname}\"></td>\n" +
	                "                <td th:text=\"${%s.phonenumber}\"></td>\n" +
	                "                <td th:text=\"${%s.businessregistrationnumber}\"></td>\n" +
	                "                <td>\n" +
	                "                    <a th:href=\"@{'/%ss/' + ${%s.ssn}}\">Detail</a>\n" +
	                "                    <a th:href=\"@{'/%ss/edit/' + ${%s.ssn}}\">Edit</a>\n" +
	                "                    <!-- Add a button to delete the %s -->\n" +
	                "                    <form th:action=\"@{'/%ss/delete/' + ${%s.ssn}}\" method=\"post\" style=\"display: inline;\">\n" +
	                "                        <button type=\"submit\">Delete</button>\n" +
	                "                    </form>\n" +
	                "                </td>\n" +
	                "            </tr>\n" +
	                "        </tbody>\n" +
	                "    </table>\n" +
	                "    <!-- Add a button to navigate to the add form page -->\n" +
	                "    <a th:href=\"@{'/%ss/add'}\" class=\"btn btn-primary\">Add %s</a>\n" +
	                "</body>\n" +
	                "</html>\n", className, className, className.toLowerCase(), className, className, className,
	                className, className, className, className.toLowerCase(), className, className.toLowerCase(),
	                className, className.toLowerCase(), className, className, className, className, className, className,
	                className, className, className.toLowerCase(), className, className.toLowerCase(), className.toLowerCase(),
	                className, className.toLowerCase(), className, className, className, className.toLowerCase(), className,
	                className, className.toLowerCase(), className, className, className.toLowerCase(), className.toLowerCase(),
	                className, className, className.toLowerCase(), className, className.toLowerCase(), className, className);

	        saveToFile(className.toLowerCase() + "/list.html", listTemplate);
	    }
	 // Generate Detail Template
	    public static void generateDetailTemplate(String className) {
	        String detailTemplate = String.format("<!DOCTYPE html>\n" +
	                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
	                "<head>\n" +
	                "    <title>%s Detail</title>\n" +
	                "</head>\n" +
	                "<body>\n" +
	                "    <h1>%s Detail</h1>\n" +
	                "    <dl>\n" +
	                "        <dt>SSN:</dt>\n" +
	                "        <dd th:text=\"${%s.ssn}\"></dd>\n" +
	                "        <dt>Full Name:</dt>\n" +
	                "        <dd th:text=\"${%s.fullname}\"></dd>\n" +
	                "        <dt>Phone Number:</dt>\n" +
	                "        <dd th:text=\"${%s.phonenumber}\"></dd>\n" +
	                "        <dt>Business Registration Number:</dt>\n" +
	                "        <dd th:text=\"${%s.businessregistrationnumber}\"></dd>\n" +
	                "    </dl>\n" +
	                "    <a th:href=\"@{'/%ss'}\">Back to %s List</a>\n" +
	                "</body>\n" +
	                "</html>\n", className, className, className.toLowerCase(), className, className, className,
	                className, className, className, className.toLowerCase(), className, className);

	        saveToFile(className.toLowerCase() + "/detail.html", detailTemplate);
	    }
	 // Generate Form Template
	    public static void generateFormTemplate(String className) {
	        String formTemplate = String.format("<!DOCTYPE html>\n" +
	                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
	                "<head>\n" +
	                "    <title>Add %s</title>\n" +
	                "</head>\n" +
	                "<body>\n" +
	                "    <h1>Add %s</h1>\n" +
	                "    <form th:action=\"@{'/%ss/create'}\" method=\"post\" th:object=\"${%s}\">\n" +
	                "        <label for=\"ssn\">SSN:</label>\n" +
	                "        <input type=\"text\" id=\"ssn\" name=\"ssn\" th:field=\"*{ssn}\"><br>\n" +
	                "        <label for=\"fullname\">Full Name:</label>\n" +
	                "        <input type=\"text\" id=\"fullname\" name=\"fullname\" th:field=\"*{fullname}\"><br>\n" +
	                "        <label for=\"phonenumber\">Phone Number:</label>\n" +
	                "        <input type=\"text\" id=\"phonenumber\" name=\"phonenumber\" th:field=\"*{phonenumber}\"><br>\n" +
	                "        <label for=\"businessregistrationnumber\">Business Registration Number:</label>\n" +
	                "        <input type=\"text\" id=\"businessregistrationnumber\" name=\"businessregistrationnumber\" th:field=\"*{businessregistrationnumber}\"><br>\n" +
	                "        <input type=\"submit\" value=\"Submit\">\n" +
	                "    </form>\n" +
	                "</body>\n" +
	                "</html>\n", className, className, className.toLowerCase(), className);

	        saveToFile(className.toLowerCase() + "/form.html", formTemplate);
	    }
	 // Generate Edit Template
	    public static void generateEditTemplate(String className) {
	        String editTemplate = String.format("<!DOCTYPE html>\n" +
	                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
	                "<head>\n" +
	                "    <title>Edit %s</title>\n" +
	                "</head>\n" +
	                "<body>\n" +
	                "    <h1>Edit %s</h1>\n" +
	                "    <form th:action=\"@{'/%ss/update/' + ${%s.ssn}}\" method=\"post\" th:object=\"${%s}\">\n" +
	                "        <input type=\"hidden\" th:field=\"*{ssn}\">\n" +
	                "        <label for=\"fullname\">Full Name:</label>\n" +
	                "        <input type=\"text\" id=\"fullname\" name=\"fullname\" th:field=\"*{fullname}\"><br>\n" +
	                "        <label for=\"phonenumber\">Phone Number:</label>\n" +
	                "        <input type=\"text\" id=\"phonenumber\" name=\"phonenumber\" th:field=\"*{phonenumber}\"><br>\n" +
	                "        <label for=\"businessregistrationnumber\">Business Registration Number:</label>\n" +
	                "        <input type=\"text\" id=\"businessregistrationnumber\" name=\"businessregistrationnumber\" th:field=\"*{businessregistrationnumber}\"><br>\n" +
	                "        <input type=\"submit\" value=\"Update\">\n" +
	                "    </form>\n" +
	                "</body>\n" +
	                "</html>\n", className, className, className.toLowerCase(), className, className, className);

	        saveToFile(className.toLowerCase() + "/edit.html", editTemplate);
	    }
	    
	 // Save generated code to a file
	    public static void saveToFile(String fileName, String content) {
	        try {
	            FileWriter writer = new FileWriter(fileName);
	            writer.write(content);
	            writer.close();
	            System.out.println(fileName + " 생성 완료");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	    