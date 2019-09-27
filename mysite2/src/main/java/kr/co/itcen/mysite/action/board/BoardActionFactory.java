package kr.co.itcen.mysite.action.board;

import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		
		Action action =null;
		if("writeform".equals(actionName)) {
			action = new WriteFormAction();
		}else if("write".equals(actionName)) {
			action = new WriteAction();
		}else if("view".equals(actionName)) {
			action = new ViewAction();
		}else if("updateform".equals(actionName)) {
			action = new UpdateFormAction();
		}else if("update".equals(actionName)) {
			action = new UpdateAction();
		}else if("delete".equals(actionName)) {
			action = new DeleteAction();
		}else {
			action = new ListAction();
		}
		return action;
	}
}


