figure(1);
plot(m,MSEidPR1);
hold on, 
plot(m,MSEidSIM1);
grid;
title("MSE id, na=nb=1");
legend("Predictie","Simulare");

%%
figure(2);
plot(m,MSEidPR2);
hold on, 
plot(m,MSEidSIM2);
grid;
title("MSE id, na=nb=2");
legend("Predictie","Simulare");

%%
figure(3);
plot(m,MSEidPR3);
hold on, 
plot(m,MSEidSIM3);
grid;
title("MSE id, na=nb=3");
legend("Predictie","Simulare");

%%
figure(4);
plot(m,MSEvalPR1);
hold on, 
plot(m,MSEvalSIM1);
grid;
title("MSE val, na=nb=1");
legend("Predictie","Simulare");

%%
figure(4);
plot(m,MSEvalPR2);
hold on, 
plot(m,MSEvalSIM2);
grid;
title("MSE val, na=nb=2");
legend("Predictie","Simulare");
ylim([0 5]), xlim([1 5])

%%
figure(5);
plot(m,MSEvalPR3);
hold on, 
plot(m,MSEvalSIM3);
grid;
title("MSE val, na=nb=3");
legend("Predictie","Simulare");
ylim([0 5]), xlim([1 5])