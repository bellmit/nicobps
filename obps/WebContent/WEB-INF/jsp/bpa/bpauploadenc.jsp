<html>
<head>
<title>OBPS | Upload Enclosures</title>
<%@include file="../common/headerfiles.jsp"%>
</head>
<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">
				<h3 class="mt-4 mb-2"
					style="font-size: 32px; border-bottom: 3px solid #005776">
					Apply for building permit:Upload Enclosures</h3>

				<div class="row border rounded mr-5 ml-2">
					<div class="col-md-12 py-4 px-5">
						<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
							prefix="fn"%>
						<form:form method="POST"
							action="submitbpaenclosures.htm?${_csrf.parameterName}=${_csrf.token}"
							modelAttribute="BpaEnclosures" name="BpaEnclosures"
							id="BpaEnclosures" enctype="multipart/form-data"
							onsubmit="javascript:return onbeforeSubmit()">
							<script
								src="resources/js/application/bpa/uploadbpaenclouseres.js"></script>

							<div class="row">
								<div class="col-md-12 py-12 px-12">
									<h5 style="border-bottom: 3px solid #005776">Upload
										Enclosures</h5>
									<table id="entrytable">
										<core:forEach items="${enclosuresList}" var="enclosur"
											varStatus="loop">
											<tr class="row">
												<td class="col-md-8"><core:if
														test="${empty enclosur.value2}">
														<form:checkbox
															path="appenclosures[${loop.index}].enclosurecode"
															value="${enclosur.key}" id="chk_${enclosur.key}"
															cssClass="chkbox chk_${enclosur.value1}" />
													</core:if> <core:if test="${not empty enclosur.value2}">
														<form:checkbox
															path="appenclosures[${loop.index}].enclosurecode"
															value="${enclosur.key}" id="chk_${enclosur.key}"
															cssClass="chkbox" />
													</core:if> <label for="chk_${enclosur.key}"> <core:out
															value="${enclosur.value}" escapeXml="enccode" /> <core:if
															test="${fn:contains(enclosur.value1, 'Y') && empty enclosur.value2}">
															<span style="color: red">*</span>
														</core:if>
												</label></td>
												<td class="col-md-3"><form:input
														path="appenclosures[${loop.index}].fileContent"
														type="file" cssClass="file" id="file_${enclosur.key}"
														patt="${enclosur.value4}" disabled="true" /> <span
													style="color: red"><form:errors
															path="appenclosures[${loop.index}].fileContent"
															cssClass="error" /></span> <span
													id="file_<core:out value="${enclosur.key}" escapeXml="true"></core:out>Msg"
													class="error" style="color: red"></span></td>
												<%-- 												<td class="col-md-1"><core:if test="${not empty enclosur.value2}"> --%>
												<!-- 														<a -->
												<%-- 															href="outputbpa.htm?applicationcode=${enclosur.value2}&enclosurecode=${enclosur.key}" --%>
												<!-- 															target="_blank">View Enclosures</a> -->
												<%-- 													</core:if></td> --%>

											</tr>
										</core:forEach>
									</table>
								</div>

								<div class="col-md-12 py-12 px-12">

									<table id="entrytable"
										style="border-top: 3px solid #005776; border-bottom: 3px solid #005776">
										<tr>
											<td></td>
											<td>
												<div class="form-group">
													<div>
														<label>Please enter the text as shown in the image
															<span class="mandatory">*</span>
														</label> <img src="./jcaptcha.jpg" id="jcaptchaimg"
															onclick="changeCaptcha();" title="Click To Reload"
															style="cursor: pointer;" />
														<form:input type="text" path="userresponsecaptcha"
															autocomplete="off" maxlength="6" />
														<%-- <img src="<core:out value="${contextPath}" escapeXml="true"></core:out>/scripts/images/icon-refresh.png" id="jcaptchaRef" onclick="changeCaptcha();" title="Click To Reload" style="cursor: pointer;"/>    --%>
														<div>
															<form:errors path="userresponsecaptcha" cssClass="error"
																style="color:red" />
														</div>
													</div>
													<div style="text-align: center; padding-top: 10px">
														<input type="submit" name="_submit" id="_submit"
															value="Upload" class="btn btn-primary b-btn" />
													</div>

													<div id="UploadEncMsg" class="formErrorContent"></div>
													<span style="color: red"> <core:out
															value="${successMsg}" escapeXml="true" /></span>
												</div>
											</td>
											<td></td>
										</tr>
									</table>

								</div>

							</div>
						</form:form>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>


