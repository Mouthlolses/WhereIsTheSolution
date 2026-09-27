package com.whereisthesolution.whereisihesolutionapp.ai

import ai.koog.agents.core.tools.annotations.LLMDescription
import ai.koog.agents.core.tools.annotations.Tool
import ai.koog.agents.core.tools.reflect.ToolSet

class AiAgentTools : ToolSet {

    @Tool
    @LLMDescription("Retorna uma mensagem de teste para verificar se as ferramentas do agente estão funcionando")
    fun testTool(): String {
        return "A ferramenta está funcionando"
    }

}