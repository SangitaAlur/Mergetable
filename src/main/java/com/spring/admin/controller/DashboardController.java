package com.spring.admin.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.spring.admin.entity.Table1;
import com.spring.admin.entity.Table2;
import com.spring.admin.entity.Table3;
import com.spring.admin.repository.Table1Repository;
import com.spring.admin.repository.Table2Repository;
import com.spring.admin.repository.Table3Repository;
import org.springframework.ui.Model;

@Controller
public class DashboardController {
	 @Autowired
	    private Table1Repository table1Repository;
	    @Autowired
	    private Table2Repository table2Repository;
	    @Autowired
	    private Table3Repository table3Repository;
	    @GetMapping("/dashboard")
	    public String dashboard(Model model) {
	        List<Table1> table1Data = table1Repository.findAll();
	        List<Table2> table2Data = table2Repository.findAll();
	        List<Table3> table3Data = table3Repository.findAll();

	        model.addAttribute("table1Data", table1Data);
	        model.addAttribute("table2Data", table2Data);
	        model.addAttribute("table3Data", table3Data);
	        return "dashboard";
	    }
	    @PostMapping("/merge")
	    public String mergeTables() {
	        List<Table1> table1Data = table1Repository.findAll();
	        List<Table2> table2Data = table2Repository.findAll();
	        table3Repository.deleteAll();
	        for (Table1 row : table1Data) {
	            Table3 newRow = new Table3();
	            newRow.setData(row.getData());
	            table3Repository.save(newRow);
	        }

	        for (Table2 row : table2Data) {
	            boolean exists = table1Data.stream().anyMatch(t -> t.getData().equals(row.getData()));
	            if (!exists) {
	                Table3 newRow = new Table3();
	                newRow.setData(row.getData());
	                table3Repository.save(newRow);
	            }
	        }

	        return "redirect:/dashboard";
	    }

}
