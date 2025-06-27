package asd.technischleerdoel.spotitube.config
import org.apache.coyote.BadRequestException
import org.springframework.context.MessageSource
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException.Forbidden
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler(private val messageSource: MessageSource) {
    private fun getMessage(code: String, locale: Locale): String =
        messageSource.getMessage(code, null, locale)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(ex: DataIntegrityViolationException, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data: " + ex.mostSpecificCause.message)

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestError(ex: IllegalArgumentException, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessage("message.BAD_REQUEST", locale))

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(ex: NoSuchElementException, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(getMessage("message.NOT_FOUND", locale))

    @ExceptionHandler(Forbidden::class)
    fun handleForbidden(ex: Forbidden, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.FORBIDDEN).body(getMessage("message.FORBIDDEN", locale))

    @ExceptionHandler(SecurityException::class)
    fun handleUnauthorized(ex: SecurityException, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("message.UNAUTHORIZED", locale))

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getMessage("message.INTERNAL_SERVER_ERROR", locale))
}
