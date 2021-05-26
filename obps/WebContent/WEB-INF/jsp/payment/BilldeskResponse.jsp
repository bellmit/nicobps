<html>
<head>
<title>OBPS</title>
<%@include file="../common/headerfiles.jsp"%>

</head>
<body>
	<div class="d-flex" id="wrapper">
		<%@include file="../common/menuside.jsp"%>
		<div id="page-content-wrapper">
			<%@include file="../common/menutop.jsp"%>
			<div class="container-fluid">

				<div class="row">
					<div class="col-md-10">
						<div class=" bg-light text-black  m-4 p-3 border rounded"
							style="width: 100%;">


							<h3>
								<core:out value="${message}" escapeXml="true" />
							</h3>
							<core:if test="${status eq '0300'}">


								<table class=""
									style="width: 70%; margin: 0px auto; border-spacing: 10px">
									<tr>
										<th colspan="2" class="font-weight-bold text-center h5 ">Payment
											Details</th>
									</tr>
									<tr>
										<td colspan="2" style="border-bottom: 2px solid #005776"></td>
									</tr>
									<tr>
										<td class=" font-weight-bold w-25 ">Transaction Code :</td>
										<td class="col-md-5 "><core:out
												value="${transactioncode}" escapeXml="true" /></td>
									</tr>
									<tr>
										<td class=" font-weight-bold">Bill desk Reference No :</td>
										<td class="col-md-5 "><core:out
												value="${billdeskreferenceNo}" escapeXml="true" /></td>
									</tr>

									<tr>
										<td class=" font-weight-bold">Bank Reference No :</td>
										<td class="col-md-5 "><core:out
												value="${bankreferenceno}" escapeXml="true" /></td>

									</tr>
									<tr>
										<td class=" font-weight-bold">Amount :</td>
										<td class="col-md-5 "><core:out value="${amount}"
												escapeXml="true" /></td>

									</tr>
								</table>
							</core:if>

						</div>



					</div>
				</div>

			</div>
		</div>
	</div>

</body>

</html>