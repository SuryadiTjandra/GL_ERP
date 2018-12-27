package ags.goldenlionerp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import kotlin.jvm.java

@SpringBootApplication
open class DistributedSystemApplication 

fun main(args: Array<String>){
	SpringApplication.run(DistributedSystemApplication::class.java, *args);
}