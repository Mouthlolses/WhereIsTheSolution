package com.whereisthesolution.whereisihesolutionapp.ai

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.google.GoogleLLMClient
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor


object AiAgent {

    private val aiAgentKey = "AQ.Ab8RN6KA-O6S-vCABde_T1yzBlZE3Mq9uSlQOUJLv-zpqUEu3g"
        ?: error("GOOGLE_API_KEY não encontrada")

    private val tools = AiAgentTools()

    private val agent = AIAgent(
        promptExecutor = MultiLLMPromptExecutor(
            GoogleLLMClient(
                apiKey = aiAgentKey
            )
        ),
        llmModel = GoogleModels.Gemini3_5Flash
    )

    suspend fun test(): String {
        return agent.run("Olá! Explique em uma frase o que é Kotlin.")
    }

}