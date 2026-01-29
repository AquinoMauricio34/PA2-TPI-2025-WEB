<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="c"  uri="jakarta.tags.core" %>
<jsp:include page="/componentes/header.jsp"/>
<jsp:include page="/componentes/body1.jsp"/>
<jsp:include page="${contenido}"/>
<jsp:include page="/componentes/body2.jsp"/>