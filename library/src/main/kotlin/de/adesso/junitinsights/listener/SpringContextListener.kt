package de.adesso.junitinsights.listener

import de.adesso.junitinsights.tools.TimestampWriter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 * Listens to the spring-context-events to register starting and stopping of the context
 */
@Component
class SpringContextListener {

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    private val timestampWriter = TimestampWriter

    /**
     * Listens for ContextRefreshedEvent
     * which appears when the context is updated or refreshed.
     * @see ContextRefreshedEvent
     */
    @EventListener(ContextRefreshedEvent::class)
    fun catchContextStart(event: ContextRefreshedEvent) {
        //log.info("### AppContextId: ${event.applicationContext.id}")
        //TODO Check if first init before closing initial, so that its not a refresh
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "context refreshed",
                "", "")
    }

    /**
     * Listens for ContextClosedEvent
     * which appears when the context is closed.
     * @see ContextClosedEvent
     */
    @EventListener(ContextClosedEvent::class)
    fun catchContextEnd(event: ContextClosedEvent) {
        //log.info("### AppContextId: ${event.applicationContext.id}")
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "context closed",
                "", "")
        //TODO: Is this method really called only once at the end?
        timestampWriter.createReport()
    }
}
