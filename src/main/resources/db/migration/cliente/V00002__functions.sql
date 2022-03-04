create or replace function despesa_mensal(OUT retorno_valor numeric, OUT retorno_variacao numeric)
language plpgsql
as $$
declare
    data_atual date default CURRENT_DATE;
begin
    select sum(l.valor) into retorno_valor
    from lancamento l
    join plano p on l.plano_credito_id = p.id
    join portador pt on pt.plano_id = p.id
    where l.mes_lancamento = extract(month from data_atual)
    and l.ano_lancamento = extract(year from data_atual);

    data_atual = data_atual - interval '1' month;

    select ((retorno_valor/(cast(sum(l.valor) as numeric)) - 1) * 100) into retorno_variacao
    from lancamento l
    join plano p on l.plano_credito_id = p.id
    join portador pt on pt.plano_id = p.id
    where l.mes_lancamento = extract(month from data_atual)
    and l.ano_lancamento = extract(year from data_atual);

    if(retorno_valor is null) then
		retorno_valor = 0;
    end if;

	if(retorno_variacao is null) then
		retorno_variacao = 0;
    end if;

	if(retorno_variacao = 0E-20) then
		retorno_variacao = 0;
    end if;
end;$$;

create or replace function receita_mensal(OUT retorno_valor numeric, OUT retorno_variacao numeric)
language plpgsql
as $$
declare
    data_atual date default CURRENT_DATE;
begin
    select sum(l.valor) into retorno_valor
    from lancamento l
    join plano p on l.plano_debito_id = p.id
    join portador pt on pt.plano_id = p.id
    where l.mes_lancamento = extract(month from data_atual)
    and l.ano_lancamento = extract(year from data_atual);

    data_atual = data_atual - interval '1' month;

    select ((retorno_valor/(cast(sum(l.valor) as numeric)) - 1) * 100) into retorno_variacao
    from lancamento l
    join plano p on l.plano_debito_id = p.id
    join portador pt on pt.plano_id = p.id
    where l.mes_lancamento = extract(month from data_atual)
    and l.ano_lancamento = extract(year from data_atual);

    if(retorno_valor is null) then
	    retorno_valor = 0;
    end if;

	if(retorno_variacao is null) then
		retorno_variacao = 0;
    end if;

	if(retorno_variacao = 0E-20) then
		retorno_variacao = 0;
    end if;
end;$$;

create or replace function despesas_por_grupo(p_mes_lancamento int, p_ano_lancamento int)
returns table(nome character varying, valor numeric)
language plpgsql
as $$
declare
data_atual date default CURRENT_DATE;
begin
    return query select
        p.nome,
		(
			select sum(l.valor)
			from lancamento l
			join plano p2
			on p2.id = l.plano_debito_id
			where p2.cod_contabil like p.cod_contabil || '%'
			and l.mes_lancamento = p_mes_lancamento
			and l.ano_lancamento = p_ano_lancamento
		) valor
	from plano p
	where p.cod_contabil like '4.%'
	and p.nivel = 1;
end;$$;

create or replace function evolucao_despesas(p_mes_lancamento int, p_ano_lancamento int)
returns table(dia integer, valor numeric)
language plpgsql
as $$
begin
    return query select
		cast(extract(day from l."data") as int) as dia,
		cast((case
			when (select sum(l2.valor) from lancamento l2 where l2."data" < l."data" and l2.mes_lancamento = p_mes_lancamento and l2.ano_lancamento = p_ano_lancamento) is null then
				sum(l.valor)
		else
			sum(l.valor) + (select sum(l2.valor) from lancamento l2 where l2."data" < l."data" and l2.mes_lancamento = p_mes_lancamento and l2.ano_lancamento = p_ano_lancamento)
		end) as numeric) as valor
	from lancamento l
	join plano p on p.id = l.plano_debito_id
	where l.mes_lancamento = p_mes_lancamento
	and l.ano_lancamento = p_ano_lancamento
	and p.cod_contabil like '4%'
	group by l."data"
	order by l."data";
end;$$;