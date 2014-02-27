package cn.edu.uestc.acmicpc.db.dto.impl.problem;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * DTO post from problem editor.
 */
public class ProblemEditDTO {

  public ProblemEditDTO() {
  }

  private String action;

  private Integer problemId;

  private String description;

  @NotNull(message = "Please enter a validate title.")
  @Length(min = 1, max = 50, message = "Please enter a validate title.")
  private String title;

  private Boolean isSpj;

  @NotNull(message = "Time limit should between 0 and 60000")
  @Range(min = 0, max = 60000, message = "Time limit should between 0 and 60000")
  private Integer timeLimit;

  @NotNull(message = "Time limit should between 0 and 60000")
  @Range(min = 0, max = 60000, message = "Time limit should between 0 and 60000")
  private Integer javaTimeLimit;

  @NotNull(message = "Memory limit should between 0 and 262144")
  @Range(min = 0, max = 262144, message = "Memory limit should between 0 and 262144")
  private Integer memoryLimit;

  @NotNull(message = "Memory limit should between 0 and 262144")
  @Range(min = 0, max = 262144, message = "Memory limit should between 0 and 262144")
  private Integer javaMemoryLimit;

  @NotNull(message = "Output limit should between 0 and 262144")
  @Range(min = 0, max = 262144, message = "Output limit should between 0 and 262144")
  private Integer outputLimit;

  private Boolean isVisible;

  private String input;

  private String output;

  private String sampleInput;

  private String sampleOutput;

  private String hint;

  private String source;

  public ProblemEditDTO(String action, Integer problemId, String description, String title, Boolean isSpj, Integer timeLimit, Integer javaTimeLimit, Integer memoryLimit, Integer javaMemoryLimit, Integer outputLimit, Boolean isVisible, String input, String output, String sampleInput, String sampleOutput, String hint, String source) {
    this.action = action;
    this.problemId = problemId;
    this.description = description;
    this.title = title;
    this.isSpj = isSpj;
    this.timeLimit = timeLimit;
    this.javaTimeLimit = javaTimeLimit;
    this.memoryLimit = memoryLimit;
    this.javaMemoryLimit = javaMemoryLimit;
    this.outputLimit = outputLimit;
    this.isVisible = isVisible;
    this.input = input;
    this.output = output;
    this.sampleInput = sampleInput;
    this.sampleOutput = sampleOutput;
    this.hint = hint;
    this.source = source;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getIsSpj() {
    return isSpj;
  }

  public void setIsSpj(Boolean isSpj) {
    this.isSpj = isSpj;
  }

  public Integer getTimeLimit() {
    return timeLimit;
  }

  public void setTimeLimit(Integer timeLimit) {
    this.timeLimit = timeLimit;
  }

  public Integer getJavaTimeLimit() {
    return javaTimeLimit;
  }

  public void setJavaTimeLimit(Integer javaTimeLimit) {
    this.javaTimeLimit = javaTimeLimit;
  }

  public Integer getMemoryLimit() {
    return memoryLimit;
  }

  public void setMemoryLimit(Integer memoryLimit) {
    this.memoryLimit = memoryLimit;
  }

  public Integer getJavaMemoryLimit() {
    return javaMemoryLimit;
  }

  public void setJavaMemoryLimit(Integer javaMemoryLimit) {
    this.javaMemoryLimit = javaMemoryLimit;
  }

  public Integer getOutputLimit() {
    return outputLimit;
  }

  public void setOutputLimit(Integer outputLimit) {
    this.outputLimit = outputLimit;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public String getSampleInput() {
    return sampleInput;
  }

  public void setSampleInput(String sampleInput) {
    this.sampleInput = sampleInput;
  }

  public String getSampleOutput() {
    return sampleOutput;
  }

  public void setSampleOutput(String sampleOutput) {
    this.sampleOutput = sampleOutput;
  }

  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }
}
