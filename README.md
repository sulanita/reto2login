# reto2login

## Queries 


    traces
    |project message ,  usuario= customDimensions['usuario'] , itemCount 
    |where message in ('NotificarUsuario','UsuarioBloqueado')
    |summarize  sum(itemCount) by message, tostring(usuario)


    traces
    | project customDimensions['usuario'], message
    | where message in ('UsuarioBloqueado')

    traces
    | project customDimensions['usuario'],customDimensions['intentos'], message
    | where message in ('NotificarUsuario')
