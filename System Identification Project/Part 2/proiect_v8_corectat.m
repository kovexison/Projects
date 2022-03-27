clearvars;
close all;
load("iddata-01.mat");
%id-array este pt. cei care nu au instalat identification toolbox
%utilizez variabilele id si val
clear id_array;
clear val_array; %nu le utilizez

%vizualizarea datelor de identificare:
figure(1);
plot(id);

y_id = id.y;
u_id = id.u;

na = 1; %de aici sunt reglate ordinele modelului 
m = 4;
nk = 0;
nb = na;

%%
vector_puteri = combinare_unica(na,nb,m);
DKid = generare_PHI(id,na,nb,nk);  %vectorul de semnale precedente
                                %echivalent cu PHI din ARX (luat de la
                                %lab6)
PHIid = phi_narx(vector_puteri,DKid,length(y_id));
THETA = PHIid\y_id; %s-au gasit coeficientii theta al polinomului NARX
 %% Erori pe datele de identificare
yhat_predictie = PHIid*THETA; %echivalent cu predictia cu un pas inainte

id_sim = iddata(yhat_predictie,id.u,id.Ts); 
%generez structura iddata pt. functiile create anterior, continand iesirea deja prezisa
Dk_simulare = generare_PHI(id_sim, na,nb,nk); %vectorii de semnale intarziate, luate din simularea anterioara
PHIsim = phi_narx(vector_puteri,Dk_simulare,length(yhat_predictie)); %generare matrice de regresori
yhat_simulare = simulare(vector_puteri,id.u,na,nb,nk,THETA);

figure(2);
plot(y_id);
hold on;
plot(yhat_predictie),
plot(yhat_simulare);
hold off;
grid, legend("y id real","predictie","simulare");
N = length(y_id);
MSE_pred = sum(1/N*(y_id - yhat_predictie).^2);
MSE_sim = sum(1/N*(y_id - yhat_simulare).^2);
title(strcat("Identificare. ","na=nb=",num2str(na),", m=",num2str(m),strcat(" MSE pred = ",strcat(num2str(MSE_pred),strcat(", MSE sim = ",num2str(MSE_sim))))));


%% Erori pe datele de validare
DKval_predictie = generare_PHI(val,na,nb,nk);
PHIval_predictie = phi_narx(vector_puteri,DKval_predictie,length(val.y));
yhat_predictie = PHIval_predictie*THETA; %echivalent cu predictia cu un pas inainte

val_sim = iddata(yhat_predictie,val.u,val.Ts); 
%generez structura iddata pt. functiile create anterior, continand iesirea deja prezisa
Dk_simulare = generare_PHI(val_sim, na,nb,nk); %vectorii de semnale intarziate, luate din simularea anterioara
PHIsim = phi_narx(vector_puteri,Dk_simulare,length(yhat_predictie)); %generare matrice de regresori
yhat_simulare = simulare(vector_puteri,val.u,na,nb,nk,THETA);

%close all;
figure(3);
plot(val.y), hold on;
plot(yhat_predictie);
plot(yhat_simulare);
hold off;
legend("y validare","predictie","simulare");
grid
N = length(yhat_predictie);
MSE_pred = sum(1/N * (val.y-yhat_predictie).^2);
MSE_sim = sum(1/N * (val.y - yhat_simulare).^2);
title(strcat("Validare. ","na=nb=",num2str(na),", m=",num2str(m),strcat(" MSE pred = ",strcat(num2str(MSE_pred),strcat(", MSE sim = ",num2str(MSE_sim))))));

%% plot grafice cu erori


function vector_puteri = combinare_unica(na,nb,m) %returneaza toate combinatiile de puteri posibile ai parametrilor

    v = zeros(1,(na+nb)*(m+1));
    for i = 0:m %popularea vectorului cu numere de la 0 la m
        M = 0;
        while(M <= (na+nb)*m)
            v(1,i+1+M)=i;
%             if(i==0) 
%                 M = 1; %pe prima iterare trebuie ca popularea sa fie corecta
%             end
            M = M + m+1;
        end
%         v(1,i+1+m+1)=i;
%         v(1,i+1+2*(m+1))=i;
    end
    vector_puteri = nchoosek(v, na+nb); %returneaza toate combinatiile posibile de lungime na+nb din vectorul v
    vector_puteri = unique(vector_puteri,'rows'); %returneaza doar liniile unice care nu sunt dublate
    N = length(vector_puteri);  %returneaza numarul de linii
    lungime_noua = N;
    i = 0;
    while(i <= N) %liniile in care suma este mai mare decat m, trebuie sa fie sterse
        i = i + 1;
        suma_puterilor = 0;
        for j = 1:na+nb
            suma_puterilor = suma_puterilor + vector_puteri(i,j); %se insumeaza numerele din coloane
        end
        if(suma_puterilor > m)
            vector_puteri(i,:) = [];
            i = i-1;
            lungime_noua = length(vector_puteri);
        end
        if(i == lungime_noua)
            break;
        end
    end
end

function PHI = generare_PHI(data, na, nb,nk)

    y = data.y;
    u = data.u;

    N = length(y);
    PHI = zeros(N, na+nb);
    for i = 1:N
        for j = 1:na
            if((i-j)>0)
                PHI(i,j) = -y(i-j);
            end
        end

        for j = 1:nb
            if((i-j-nk)>0)
                PHI(i, na+j) = u(i-j-nk);
            end
        end
    end

end

function PHI = phi_narx(vector_puteri, d_k, N)
%genereaza matricea regresorilor pt. ARX neliniar
    PHI = ones(N,length(vector_puteri));
    for i = 1:N
        for j = 1:length(d_k(1,:)) %=na+nb
            for k = 1:length(vector_puteri)
                PHI(i,k) = PHI(i,k)*d_k(i,j)^vector_puteri(k,j);
            end
        end
    end

end

function y = simulare(vector_puteri, u, na,nb,nk,THETA)
    
    N = length(u);
    d_k = zeros(N, na+nb);
    y = zeros(N,1);
    y(1,1) = 7.5;
    PHI = ones(N,length(vector_puteri));
    for i = 1:N
        for j = 1:na
            if((i-j)>0)
                d_k(i,j) = -y(i-j);
            end
        end

        for j = 1:nb
            if((i-j-nk)>0)
                d_k(i, na+j) = u(i-j-nk);
            end
        end
        for k = 1:(na+nb)
            PHI(i,k) = PHI(i,k)*d_k(i,j)^vector_puteri(k,j);
        end
        y(i+1,1) = PHI(i,:)*THETA;
    end
        y = y(1:N);
end