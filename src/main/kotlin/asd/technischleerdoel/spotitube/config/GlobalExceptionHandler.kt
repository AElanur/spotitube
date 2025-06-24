package asd.technischleerdoel.spotitube.config
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler(private val messageSource: MessageSource) {
    private fun getMessage(code: String, locale: Locale): String =
        messageSource.getMessage(code, null, locale)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(ex: NoSuchElementException, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(getMessage("message.NOT_FOUND", locale))

    @ExceptionHandler(SecurityException::class)
    fun handleUnauthorized(ex: SecurityException, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("message.UNAUTHORIZED", locale))


    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, locale: Locale): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.")
}
