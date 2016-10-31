package be.insaneprogramming.cleanarch

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Runner {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Runner::class.java, *args)
        }
    }
}
