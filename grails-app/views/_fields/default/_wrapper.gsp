<%@ page defaultCodec="html" %>
<div class="fieldcontain col-6 form-group ${invalid ? 'error' : ''}">
	<label class="col-form-label" for="${property}">${label}
		<g:if test="${required}"><span class="required-indicator">*</span></g:if>
	</label>
	<%= widget %>
	<g:if test="${invalid}"><span class="help-inline">${errors.join('<br>')}</span></g:if>
</div>