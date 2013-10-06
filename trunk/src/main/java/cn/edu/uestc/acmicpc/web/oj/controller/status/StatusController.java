package cn.edu.uestc.acmicpc.web.oj.controller.status;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.code.CodeDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusInformationDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.SubmitDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.*;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.view.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * TODO(mzry1992)
 */
@Controller
@RequestMapping("/status")
public class StatusController extends BaseController {

  private StatusService statusService;
  private ProblemService problemService;
  private LanguageService languageService;
  private CodeService codeService;
  private GlobalService globalService;
  private CompileInfoService compileInfoService;

  @Autowired
  public void setCompileInfoService(CompileInfoService compileInfoService) {
    this.compileInfoService = compileInfoService;
  }

  @Autowired
  public void setGlobalService(GlobalService globalService) {
    this.globalService = globalService;
  }

  @Autowired
  public void setCodeService(CodeService codeService) {
    this.codeService = codeService;
  }

  @Autowired
  public void setLanguageService(LanguageService languageService) {
    this.languageService = languageService;
  }

  @Autowired
  public void setProblemService(ProblemService problemService) {
    this.problemService = problemService;
  }

  @Autowired
  public void setStatusService(StatusService statusService) {
    this.statusService = statusService;
  }

  /**
   * TODO(mzry1992)
   * @return
   */
  @RequestMapping("list")
  @LoginPermit(NeedLogin = false)
  public String list() {
    return "status/statusList";
  }

  /**
   * TODO(mzry1992)
   * @return
   */
  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> search(HttpSession session,
                             @RequestBody StatusCondition statusCondition) {
    Map<String, Object> json = new HashMap<>();
    try{
      UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");
      statusCondition.contestId = -1;
      if(currentUser == null ||
          currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
        statusCondition.isVisible = true;
      Long count = statusService.count(statusCondition);
      PageInfo pageInfo = buildPageInfo(count, statusCondition.currentPage,
          Global.RECORD_PER_PAGE, "", null);
      List<StatusListDTO> statusListDTOList = statusService.getStatusList(statusCondition,
          pageInfo);
      for (StatusListDTO statusListDTO : statusListDTOList) {
        statusListDTO.setReturnType(globalService.getReturnDescription(
            statusListDTO.getReturnTypeId(), statusListDTO.getCaseNumber()));
      }

      json.put("result", "success");
      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("statusList", statusListDTOList);
    }catch(AppException e){
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }catch(Exception e){
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("submit")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody
  Map<String, Object> submit(HttpSession session,
                             @RequestBody @Valid SubmitDTO submitDTO,
                             BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");

        if (submitDTO.getProblemId() == null)
          throw new AppException("Wrong problem id.");
        ProblemDTO problemDTO = problemService.getProblemDTOByProblemId(submitDTO.getProblemId());
        if (problemDTO == null)
          throw new AppException("Wrong problem id.");
        if (!problemDTO.getIsVisible() &&
            currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
          throw new AppException("You have no permission to submit this problem.");

        //TODO(mzry1992) Check contest id after contest controller has been completed.

        if (submitDTO.getLanguageId() == null)
          throw new AppException("Please select a language.");
        if (languageService.getLanguageName(submitDTO.getLanguageId()) == null)
          throw new AppException("No such language.");

        Integer codeId = codeService.createNewCode(CodeDTO.builder()
            .setContent(submitDTO.getCodeContent())
            .build());
        if (codeId == null)
          throw new AppException("Error while saving you code.");

        statusService.createNewStatus(StatusDTO.builder()
            .setCodeId(codeId)
            .setContestId(submitDTO.getContestId())
            .setLanguageId(submitDTO.getLanguageId())
            .setProblemId(submitDTO.getProblemId())
            .setTime(new Timestamp(new Date().getTime()))
            .setUserId(currentUser.getUserId())
            .setLength(submitDTO.getCodeContent().length())
            .build());
        json.put("result", "success");
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    return json;
  }

  @RequestMapping("info/{statusId}")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody
  Map<String, Object> info(HttpSession session,
                           @PathVariable Integer statusId) {
    Map<String, Object> json = new HashMap<>();
    try{
      UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");
      StatusInformationDTO statusInformationDTO = statusService.getStatusInformation(statusId);
      if (statusInformationDTO == null)
        throw new AppException("No such status.");
      if (currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal() &&
          !statusInformationDTO.getUserId().equals(currentUser.getUserId()))
        throw new AppException("You have no permission to view this code.");
      json.put("result", "success");
      json.put("code", statusInformationDTO.getCodeContent());
      if (statusInformationDTO.getCompileInfoId() != null) {
        json.put("compileInfo", compileInfoService.getCompileInfo(
            statusInformationDTO.getCompileInfoId()));
      }
    }catch(AppException e){
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }catch(Exception e){
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }
}