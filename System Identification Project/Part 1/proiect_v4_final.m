clear all;
close all;

load('proj_fit_01.mat');

Y = id.Y;
Y = Y(:);
y_val = val.Y;
y_val = y_val(:);

m = 28;
MSE_id = zeros(1,m);
MSE_val = zeros(1,m);

for grad = 1:m

PHI_id = calculeazaPHI(id.X{1},id.X{2},grad);

THETA = PHI_id\Y;   % asta o singura data se calculeaza!! doar pentru datele de identificare
yhat_id = PHI_id*THETA;

MSE_id(1,grad) = mean((Y - yhat_id).^2);

PHI_val = calculeazaPHI(val.X{1}, val.X{2}, grad);
yhat_val = PHI_val * THETA;

MSE_val(1,grad) = mean((y_val - yhat_val).^2);

end

figure();
grad_vect = 1:1:m;
plot(grad_vect, MSE_id), hold on
plot(grad_vect, MSE_val), hold off
title("MSE pe date de identificare vs. MSE pe date de validare");
xlabel("grad"), ylabel("MSE");
grid, legend("MSE - identificare", "MSE - validare");

%%
%se cauta gradul aproximatorului unde eroarea este cea mai mica
[msemin, grad] = min(MSE_val)
PHI_id = calculeazaPHI(id.X{1},id.X{2},grad);

THETA = PHI_id\Y;  
yhat_id = PHI_id*THETA;
mse_id = mean((Y - yhat_id).^2);
PHI_val = calculeazaPHI(val.X{1}, val.X{2}, grad);
yhat_val = PHI_val * THETA;
mse_val = mean((y_val - yhat_val).^2);

figure(),
mesh(id.X{1},id.X{2},reshape(yhat_id, [length(id.X{1}), length(id.X{2})]), 'EdgeColor',[0 0.4470 0.7410])
xlabel("x_1"), ylabel("x_2"), zlabel("y_{hat id}");
title(strcat("Yhat vs. Y pe datele de identificate; gradul aproximatorului este ", num2str(grad)));
hold on, mesh(id.X{1},id.X{2},id.Y, 'EdgeColor',[1 0 0]);
hold off;
legend("Y_{id} prezis", "Y_{id} furnizat");


figure();
mesh(val.X{1},val.X{2},reshape(yhat_val, [length(val.X{1}), length(val.X{2})]), 'EdgeColor',[0 0.4470 0.7410]);
xlabel("x_1"), ylabel("x_2"), zlabel("y_{hat val}")
title(strcat("iesirea prezisa vs.iesirea masurata pe datele de validare; MSE = ", num2str(mse_val)));
hold on, mesh(val.X{1}, val.X{2}, val.Y, 'EdgeColor',[0.6350 0.0780 0.1840]);
hold off;
legend("Y_{val} prezis","Y_{val} furnizat" )

%% vizualizare cu grad maxim - cazul de supraantrenare\

PHI_id = calculeazaPHI(id.X{1},id.X{2},28);

THETA = PHI_id\Y;  
yhat_id = PHI_id*THETA;
mse_id = mean((Y - yhat_id).^2);
PHI_val = calculeazaPHI(val.X{1}, val.X{2}, 28);
yhat_val = PHI_val * THETA;
mse_val = mean((y_val - yhat_val).^2);

figure(),
mesh(id.X{1},id.X{2},reshape(yhat_id, [length(id.X{1}), length(id.X{2})]), 'EdgeColor',[0 0.4470 0.7410])
xlabel("x_1"), ylabel("x_2"), zlabel("y_{hat id}");
title(strcat("Yhat pe datele de identificate; gradul aproximatorului este ", num2str(grad)));
hold on, mesh(id.X{1},id.X{2},id.Y, 'EdgeColor',[1 0 0]);
hold off;
legend("Y_{id} prezis", "Y_{id} furnizat");


figure();
mesh(val.X{1},val.X{2},reshape(yhat_val, [length(val.X{1}), length(val.X{2})]), 'EdgeColor',[0 0.4470 0.7410]);
xlabel("x_1"), ylabel("x_2"), zlabel("y_{hat val}")
title(strcat("iesirea prezisa pe datele de validare; MSE = ", num2str(mse_val)));
hold on, mesh(val.X{1}, val.X{2}, val.Y, 'EdgeColor',[0.6350 0.0780 0.1840]);
hold off;
legend("Y_{val} prezis","Y_{val} furnizat" )



function phi = calculeazaPHI(x1,x2,m)

phi = [];

for i = 1:length(x1)
    for j = 1:length(x2)
        rand = [];
        for p1 = 0:m
            for p2 = 0:m
                if(p1+p2 <= m)
                    rand = [rand x1(i)^p1*x2(j)^p2];
                end
            end
        end
        phi = [phi; rand];
    end
end

end