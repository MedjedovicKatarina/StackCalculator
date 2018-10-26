package calc;

import java.util.LinkedHashMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("{id}")
public class CalculatorServer {
	
	private static LinkedHashMap<Integer, StackCalculator> calculators = new LinkedHashMap<Integer, StackCalculator>();
	
	public CalculatorServer() {
		
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/push/{n}")
	public String push(@PathParam("id") int id, @PathParam("n") int n) {
		String resource=null;
		if (calculators.containsKey(id)) {
			calculators.get(id).getStack().push(n);
			resource="<h1>Push" + n+ "</h1>";
		} else {
			StackCalculator newCalculator = new StackCalculator();
			newCalculator.getStack().push(n);
			calculators.put(id, new StackCalculator());
			resource="<h1>New" + n+ "</h1>";
		}
		return resource;
	}

}
