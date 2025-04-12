# Sobreeeee Quartz

Durante uma demanda surgiu Quartz, que nunca tinha trabalhado. Consegui resolver o problema, mas depois dei uma parada e estudei para entender melhor.

Na demanda do trabalho a arquitetura utilizava JobStoreCMT e PostgreSQLDeletate, que é algo que não vai ser aqui agora, mas futuramente penso em colocar aqui um projeto. A única diferença com o RAMJobStore é que (1) a informação fica salva no banco de dados e (2) como um reflexo os jobs e triggers ficam salvos e nada se perde no caso de uma parada ou reinicialização.

Noix!!!