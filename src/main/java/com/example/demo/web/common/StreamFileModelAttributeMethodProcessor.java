package com.example.demo.web.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;

public class StreamFileModelAttributeMethodProcessor extends ModelAttributeMethodProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(StreamFileModelAttributeMethodProcessor.class);

	public StreamFileModelAttributeMethodProcessor() {
		super(false);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return StreamFile.class.equals(parameter.getParameterType());
	}

	@Override
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {

		HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
		ServletRequestParameterPropertyValues pvs = new ServletRequestParameterPropertyValues(httpRequest);

		pvs.add("contentType", httpRequest.getContentType());
		pvs.add("contentLength", httpRequest.getContentLengthLong());
		String rowXFileName = httpRequest.getHeader("X-FILE-NAME");
		if (rowXFileName == null || "".equals(rowXFileName)) {
			pvs.add("fileName", rowXFileName);
		} else {
			try {
				String urlDecodedValue = URLDecoder.decode(rowXFileName, "iso-8859-1");
				String fileName = new String(urlDecodedValue.getBytes(StandardCharsets.ISO_8859_1),
						StandardCharsets.UTF_8);
				LOGGER.debug("X-FILE-NAME : row={}, converted={}", rowXFileName, fileName);
				pvs.add("fileName", fileName);
			} catch (UnsupportedEncodingException e) {
				pvs.add("fileName", rowXFileName);
			}
		}

		try {
			pvs.add("inputStream", httpRequest.getInputStream());
		} catch (IOException e) {
			pvs.add("inputStream", null);
		}

		binder.bind(pvs);
	}
}
